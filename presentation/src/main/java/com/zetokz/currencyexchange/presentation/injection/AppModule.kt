package com.zetokz.currencyexchange.presentation.injection

import android.content.Context
import com.zetokz.currencyexchange.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@Module
class AppModule {

    @Provides @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext
}