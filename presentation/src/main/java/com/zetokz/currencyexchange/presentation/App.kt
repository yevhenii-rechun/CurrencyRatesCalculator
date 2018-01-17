package com.zetokz.currencyexchange.presentation

import android.content.Context
import android.support.multidex.MultiDex
import com.zetokz.currencyexchange.presentation.injection.AppComponent
import com.zetokz.currencyexchange.presentation.injection.DaggerAppComponent
import com.zetokz.currencyexchange.presentation.injection.applyAutoInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
class App : DaggerApplication() {

    companion object {

        lateinit var instance: App
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()

        instance = this

        initDebugTools()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build().apply { appComponent = this }

    private fun initDebugTools() {
        Timber.plant(Timber.DebugTree())
    }
}