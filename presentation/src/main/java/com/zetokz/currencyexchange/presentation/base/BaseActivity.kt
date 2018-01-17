package com.zetokz.currencyexchange.presentation.base

import com.arellomobile.mvp.MvpAppCompatActivity
import com.zetokz.currencyexchange.presentation.injection.Injectable


/**
 * Created by Denys Nykyforov on 20.10.17 13:21
 * Copyright (c) 2017. All right reserved
 *
 * Last modified 20.10.17 13:20
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView, Injectable {

    override fun close() {
        finish()
    }
}