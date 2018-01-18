package com.zetokz.data.injection

import com.zetokz.data.repository.CurrencyRatesRepository
import com.zetokz.data.repository.CurrencyRatesRepositoryImpl
import com.zetokz.data.repository.HostHealthRepository
import com.zetokz.data.repository.HostHealthRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Denys Nykyforov on 23.11.2017
 * Copyright (c) 2017. All right reserved
 */
@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun provideCurrencyRatesRepository(repository: CurrencyRatesRepositoryImpl): CurrencyRatesRepository

    @Binds
    internal abstract fun provideHostHealthRepository(repository: HostHealthRepositoryImpl): HostHealthRepository
}