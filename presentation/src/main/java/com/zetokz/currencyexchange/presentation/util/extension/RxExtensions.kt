package com.zetokz.currencyexchange.presentation.util.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Denys Nykyforov on 08.12.2017
 * Copyright (c) 2017. All right reserved
 */

/**
 * disposable -= observable.subscribe()
 */
operator fun CompositeDisposable.minusAssign(disposable: Disposable?) {
    if (disposable != null) {
        remove(disposable)
    }
}