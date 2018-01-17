package com.zetokz.data.network

import com.zetokz.data.model.CurrencyRateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
interface CurrencyRateService {

    @GET("latest")
    fun fetchCurrencies(@Query("base") base: String): Single<CurrencyRateResponse>
}