package com.zetokz.currencyexchange.presentation.base

import com.arellomobile.mvp.MvpAppCompatActivity
import com.zetokz.currencyexchange.presentation.injection.Injectable

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView, Injectable {

    override fun close() {
        finish()
    }
}