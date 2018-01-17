package com.zetokz.data.repository

import com.zetokz.data.model.Currency
import com.zetokz.data.network.CurrencyRateService
import dagger.Reusable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@Reusable
internal class CurrencyRatesRepositoryImpl @Inject constructor(
    private val currencyRateService: CurrencyRateService
) : CurrencyRatesRepository {

    override fun getCurrencies(base: String): Single<List<Currency>> =
        currencyRateService.fetchCurrencies(base)
            .map { it.rates.asSequence().map { Currency(it.key, it.value) }.toList() }
            .subscribeOn(Schedulers.io())
}