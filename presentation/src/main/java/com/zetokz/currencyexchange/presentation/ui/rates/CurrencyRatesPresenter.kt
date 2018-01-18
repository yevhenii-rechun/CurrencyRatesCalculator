package com.zetokz.currencyexchange.presentation.ui.rates

import com.arellomobile.mvp.InjectViewState
import com.zetokz.currencyexchange.presentation.base.BasePresenter
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.model.toCurrencyItems
import com.zetokz.currencyexchange.presentation.util.extension.minusAssign
import com.zetokz.data.interactor.CurrencyRatesInteractor
import com.zetokz.data.model.Currency
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@InjectViewState
class CurrencyRatesPresenter @Inject constructor(
    private val currencyRatesInteractor: CurrencyRatesInteractor
) : BasePresenter<CurrencyRatesView>() {

    private var baseCurrency = "USD"
        set(value) {
            field = value
            baseChanged()
        }
    private var isOffline = false
    private var baseAmount = BehaviorSubject.createDefault(100.0)
    private var latestCurrencies = listOf<Currency>()

    private var currenciesDisposable = Disposables.empty()
    private var amountDisposable = Disposables.empty()

    override fun onFirstViewAttach() {
        observeCurrencies()
        observeAmountChanges()
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    private fun observeAmountChanges() {
        disposables -= amountDisposable
        amountDisposable = baseAmount.toFlowable(BackpressureStrategy.LATEST)
            .distinctUntilChanged()
            .map { latestCurrencies.toCurrencyItems(it) }
            .filter { it.isNotEmpty() }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleCurrencyChanges, ::handleError)
        disposables += amountDisposable
    }

    fun onCurrencyClicked(item: CurrencyItem) {
        if (isOffline) viewState.showOfflineView()
        else baseCurrency = item.currencyName
    }

    fun onMainCountChanged(baseAmount: Double) {
        this.baseAmount.onNext(baseAmount)
    }

    fun onRetryConnectionClicked() {
        viewState.toggleConnectionProgress(true)
        observeCurrencies()
    }

    private fun observeCurrencies() {
        disposables -= currenciesDisposable
        currenciesDisposable = currencyRatesInteractor.observeCurrencies(baseCurrency)
            .doOnNext { latestCurrencies = it }
            .map { it.toCurrencyItems(baseAmount.value) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleCurrencyChanges, ::handleError)
        disposables += currenciesDisposable
    }

    private fun baseChanged() {
        currencyRatesInteractor.changeBase(baseCurrency)
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    private fun handleCurrencyChanges(currencyItems: List<CurrencyItem>) {
        isOffline = false
        viewState.hideOfflineView()
        viewState.showCurrencies(currencyItems)
    }

    private fun handleError(throwable: Throwable) {
        isOffline = true
        viewState.showOfflineView()
        viewState.toggleConnectionProgress(false)
    }
}