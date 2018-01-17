package com.zetokz.data.model

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
data class CurrencyRateResponse(
    val base: String,
    val date: String,
    val rates: HashMap<String, Double>
)