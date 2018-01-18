package com.zetokz.currencyexchange.presentation.ui.rates.adapter

import com.zetokz.currencyexchange.presentation.base.adapter.SimpleListIdentifiableAdapter
import com.zetokz.currencyexchange.presentation.model.CurrencyItem

/**
 * Created by Yevhenii Rechun on 11/27/17.
 * Copyright © 2017. All rights reserved.
 */
class CurrencyRateAdapter(
    onCurrencyClickedAction: (CurrencyItem) -> Unit
) : SimpleListIdentifiableAdapter() { //todo: need to test performance with QueueAdapter

    init {
        delegatesManager.addDelegate(CurrencyRateAdapterDelegate().apply {
            setOnItemClickListener(actionOnlyItem = onCurrencyClickedAction)
        })
    }
}