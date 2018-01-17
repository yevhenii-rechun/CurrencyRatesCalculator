package com.zetokz.data.interactor

import com.zetokz.data.model.Currency
import com.zetokz.data.repository.CurrencyRatesRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
class CurrencyRatesInteractor @Inject constructor(
    private val currencyRatesRepository: CurrencyRatesRepository
) {

    private companion object {
        private const val DEFAULT_REFRESH_PERIOD_SECONDS = 5L
    }

    private var baseCurrency = BehaviorSubject.create<String>()

    fun getCurrencies(base: String): Single<List<Currency>> =
        currencyRatesRepository.getCurrencies(base)

    fun observeCurrencies(base: String): Flowable<List<Currency>> =
        baseCurrency
            .toFlowable(BackpressureStrategy.LATEST)
            .switchMap { getCurrenciesWithInterval(it) }
            .also { baseCurrency.onNext(base) }

    private fun getCurrenciesWithInterval(base: String, period: Long = DEFAULT_REFRESH_PERIOD_SECONDS) =
        Flowable.interval(0, period, TimeUnit.SECONDS, Schedulers.computation())
            .flatMap { getCurrencies(base).toFlowable() }

    fun changeBase(base: String) {
        baseCurrency.onNext(base)
    }
}