package com.zetokz.currencyexchange.presentation.ui.rates

import com.arellomobile.mvp.InjectViewState
import com.zetokz.currencyexchange.presentation.base.BasePresenter
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.model.toCurrencyItems
import com.zetokz.data.interactor.CurrencyRatesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import java.math.BigDecimal
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
    private var baseAmount = BigDecimal(100)

    override fun onFirstViewAttach() {
        disposables += currencyRatesInteractor.observeCurrencies(baseCurrency)
            .map { it.toCurrencyItems(baseAmount.toDouble()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleCurrencyChanges, ::handleError)
    }

    fun onCurrencyClicked(item: CurrencyItem) {
        baseCurrency = item.currencyName
    }

    private fun baseChanged() {
        currencyRatesInteractor.changeBase(baseCurrency)
        viewState.showBase(CurrencyInputItem(baseCurrency, baseAmount))
    }

    fun onMainCountChanged(item: CurrencyInputItem) {
//        handleCurrencyChanges()
    }

    private fun handleCurrencyChanges(currencyItems: List<CurrencyItem>) {
        viewState.showCurrencies(currencyItems)
    }

    private fun handleError(throwable: Throwable) {
        Timber.d(throwable)
    }
}