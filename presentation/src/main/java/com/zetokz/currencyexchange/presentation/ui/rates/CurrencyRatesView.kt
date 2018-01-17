package com.zetokz.currencyexchange.presentation.ui.rates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zetokz.currencyexchange.presentation.base.BaseView
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
interface CurrencyRatesView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCurrencies(currencyItems: List<CurrencyItem>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showBase(currencyInputItem: CurrencyInputItem)
}