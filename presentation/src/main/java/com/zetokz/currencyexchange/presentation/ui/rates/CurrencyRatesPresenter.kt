package com.zetokz.currencyexchange.presentation.ui.rates

import com.arellomobile.mvp.InjectViewState
import com.zetokz.currencyexchange.presentation.base.BasePresenter
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.model.toCurrencyItems
import com.zetokz.currencyexchange.presentation.util.extension.minusAssign
import com.zetokz.data.interactor.ConnectionInteractor
import com.zetokz.data.interactor.CurrencyRatesInteractor
import com.zetokz.data.model.Currency
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@InjectViewState
class CurrencyRatesPresenter @Inject constructor(
    private val currencyRatesInteractor: CurrencyRatesInteractor,
    private val connectionInteractor: ConnectionInteractor
) : BasePresenter<CurrencyRatesView>() {

    private var baseCurrency = "USD"
        set(value) {
            field = value
            baseChanged()
        }
    private var isOffline = false
        set(value) {
            field = value
            if (value) observeInternetConnection()
            else connectionDisposable.dispose()
        }
    private var baseAmount = BehaviorSubject.createDefault(100.0)
    private var latestCurrencies = Pair<String, List<Currency>>(baseCurrency, listOf<Currency>())

    private var connectionDisposable = Disposables.empty()
    private var currenciesDisposable = Disposables.empty()
    private var amountDisposable = Disposables.empty()

    override fun onFirstViewAttach() {
        observeCurrencies()
        observeAmountChanges()
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    fun onCurrencyClicked(item: CurrencyItem) {
        if (isOffline) viewState.showOfflineView()
        else baseCurrency = item.currencyName
    }

    fun onBaseAmountChanged(baseAmount: Double) {
        this.baseAmount.onNext(baseAmount)
    }

    fun onRetryConnectionClicked() {
        viewState.toggleConnectionProgress(true)
        observeCurrencies()
    }

    private fun observeCurrencies() {
        disposables -= currenciesDisposable
        currenciesDisposable = currencyRatesInteractor.observeCurrencies(baseCurrency)
            .doOnNext { latestCurrencies = Pair(baseCurrency, it) }
            .map { it.toCurrencyItems(baseAmount.value) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { restoreLastBaseCurrency() }
            .subscribe(::handleCurrencyChanges, ::handleError)
        disposables += currenciesDisposable
    }

    private fun observeInternetConnection() {
        disposables -= connectionDisposable
        connectionDisposable = connectionInteractor.observeInternetConnection()
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleConnectionChanges, Timber::d)
        disposables += connectionDisposable
    }

    private fun handleConnectionChanges(isConnected: Boolean) {
        if (isConnected) observeCurrencies()
    }

    private fun observeAmountChanges() {
        disposables -= amountDisposable
        amountDisposable = baseAmount.toFlowable(BackpressureStrategy.LATEST)
            .distinctUntilChanged()
            .map { latestCurrencies.second.toCurrencyItems(it) }
            .filter { it.isNotEmpty() }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleAmountChanges, ::handleError)
        disposables += amountDisposable
    }

    private fun restoreLastBaseCurrency() {
        baseCurrency = latestCurrencies.first
    }

    private fun baseChanged() {
        currencyRatesInteractor.changeBase(baseCurrency)
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    private fun handleAmountChanges(currencyItems: List<CurrencyItem>) {
        viewState.showCurrencies(currencyItems)
    }

    private fun handleCurrencyChanges(currencyItems: List<CurrencyItem>) {
        isOffline = false
        viewState.hideOfflineView()
        viewState.showCurrencies(currencyItems)
    }

    private fun handleError(throwable: Throwable) {
        Timber.d(throwable)
        isOffline = true
        viewState.showOfflineView()
        viewState.toggleConnectionProgress(false)
    }
}