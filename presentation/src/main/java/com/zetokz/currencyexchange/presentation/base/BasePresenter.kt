package com.zetokz.currencyexchange.presentation.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yevhenii Rechun on 1/16/18.
 * Copyright © 2017. All rights reserved.
 */
abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    protected val disposables by lazy(::CompositeDisposable)

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}