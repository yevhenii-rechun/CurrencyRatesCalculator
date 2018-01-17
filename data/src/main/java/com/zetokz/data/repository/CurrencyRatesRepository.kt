package com.zetokz.data.repository

import com.zetokz.data.model.Currency
import io.reactivex.Single

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
interface CurrencyRatesRepository {

    fun getCurrencies(base: String): Single<List<Currency>>
}