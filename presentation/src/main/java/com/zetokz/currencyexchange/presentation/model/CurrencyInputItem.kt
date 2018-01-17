package com.zetokz.currencyexchange.presentation.model

import java.math.BigDecimal

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
data class CurrencyInputItem(
    val currencyName: String,
    val count: BigDecimal
) : Identifiable {
    override val id: Long
        get() = currencyName.hashCode().toLong()
}