package com.zetokz.currencyexchange.presentation.ui.rates

import com.arellomobile.mvp.InjectViewState
import com.zetokz.currencyexchange.presentation.base.BasePresenter
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.model.toCurrencyItems
import com.zetokz.data.interactor.CurrencyRatesInteractor
import com.zetokz.data.model.Currency
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
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
    private val currencyRatesInteractor: CurrencyRatesInteractor
) : BasePresenter<CurrencyRatesView>() {

    private var baseCurrency = "USD"
        set(value) {
            field = value
            baseChanged()
        }
    private var baseAmount = BehaviorSubject.createDefault(100.0)
    private var latestCurrencies = listOf<Currency>()

    override fun onFirstViewAttach() {
        observeCurrencies()
        observeAmountChanges()
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    private fun observeAmountChanges() {
        disposables += baseAmount.toFlowable(BackpressureStrategy.LATEST)
            .distinctUntilChanged()
            .map { latestCurrencies.toCurrencyItems(it) }
            .filter { it.isNotEmpty() }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleCurrencyChanges, ::handleError)
    }

    fun onCurrencyClicked(item: CurrencyItem) {
        baseCurrency = item.currencyName
    }

    fun onMainCountChanged(baseAmount: Double) {
        this.baseAmount.onNext(baseAmount)
    }

    private fun observeCurrencies() {
        disposables += currencyRatesInteractor.observeCurrencies(baseCurrency)
            .doOnNext { latestCurrencies = it }
            .map { it.toCurrencyItems(baseAmount.value) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleCurrencyChanges, ::handleError)
    }

    private fun baseChanged() {
        currencyRatesInteractor.changeBase(baseCurrency)
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount.value))
    }

    private fun handleCurrencyChanges(currencyItems: List<CurrencyItem>) {
        viewState.showCurrencies(currencyItems)
    }

    private fun handleError(throwable: Throwable) {
        Timber.d(throwable)
    }
}