package com.zetokz.currencyexchange.presentation.model

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
data class CurrencyInputItem(
    val currencyName: String,
    val count: Double
) : Identifiable {
    val info: CurrencyInfo? = CurrencyInfo.getInfoByKey(currencyName)
    override val id = currencyName.hashCode().toLong()
}