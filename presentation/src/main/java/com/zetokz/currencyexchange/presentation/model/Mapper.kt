package com.zetokz.currencyexchange.presentation.model

import com.zetokz.data.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Yevhenii Rechun on 1/18/18.
 * Copyright © 2017. All rights reserved.
 */

fun List<Currency>.toCurrencyItems(multiplier: Double, scale: Int = 3) = asSequence()
    .map { it.toCurrencyItem(multiplier, scale) }
    .toList()

fun Currency.toCurrencyItem(multiplier: Double, scale: Int = 3) = CurrencyItem(
    currencyName,
    BigDecimal(count * multiplier).setScale(scale, RoundingMode.UP),
    CurrencyInfo.getInfoByKey(currencyName)
)