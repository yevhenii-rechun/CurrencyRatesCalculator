package com.zetokz.currencyexchange.presentation.util.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * disposable -= observable.subscribe()
 */
operator fun CompositeDisposable.minusAssign(disposable: Disposable?) {
    if (disposable != null) {
        remove(disposable)
    }
}