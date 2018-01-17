package com.zetokz.currencyexchange.presentation.injection

import com.zetokz.currencyexchange.presentation.App
import com.zetokz.data.injection.NetworkModule
import com.zetokz.data.injection.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivitiesModule::class,
        RepositoryModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(application: App): Builder

        fun build(): AppComponent
    }

}