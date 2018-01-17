package com.zetokz.currencyexchange.presentation.injection

import com.zetokz.currencyexchange.presentation.ui.rates.CurrencyRatesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@Module
interface ActivitiesModule {

    @ActivityScope @ContributesAndroidInjector
    fun contributePushNotificationSettingsActivity(): CurrencyRatesActivity

}