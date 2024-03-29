package com.zetokz.data.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zetokz.data.BuildConfig
import com.zetokz.data.network.CurrencyRateService
import com.zetokz.data.network.HealthCheckerService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
@Module
class NetworkModule {

    @Provides @Singleton
    internal fun provideHttpLogInterceptor(): Interceptor = LoggingInterceptor.Builder()
        .loggable(BuildConfig.DEBUG)
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request("Request")
        .response("Response")
        .build()

    @Provides @Singleton
    internal fun provideOkHttp(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    internal fun provideRxCallAdapterFactory(gson: Gson): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides @Singleton
    internal fun provideRetrofit(
        gson: Gson,
        httpClient: OkHttpClient,
        callFactory: CallAdapter.Factory,
        @Named("base-url") baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(callFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .baseUrl(baseUrl)
        .build()

    @Provides @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides @Singleton
    @Named("base-url")
    internal fun provideBaseUrl() = "https://api.exchangerate.host" //have to be moved to config file

    @Provides @Singleton
    internal fun provideCurrencyRateService(retrofit: Retrofit) = retrofit.create(CurrencyRateService::class.java)

    @Provides @Singleton
    internal fun provideHealthCheckerService(retrofit: Retrofit) = retrofit.create(HealthCheckerService::class.java)

}