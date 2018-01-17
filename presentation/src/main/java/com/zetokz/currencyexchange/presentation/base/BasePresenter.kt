package com.zetokz.currencyexchange.presentation.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Denys Nykyforov on 24.10.2017
 * Copyright (c) 2017. All right reserved
 */
abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    protected val disposables by lazy(::CompositeDisposable)

}