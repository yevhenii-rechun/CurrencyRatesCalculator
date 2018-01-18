package com.zetokz.currencyexchange.presentation.util.extension

import android.view.View

/**
 * Created by Yevhenii Rechun on 1/18/18.
 * Copyright © 2017. All rights reserved.
 */

internal fun View.changeVisibility(visible: Boolean, keep: Boolean = false) {
    visibility = when {
        visible -> View.VISIBLE
        !visible && keep -> View.INVISIBLE
        !visible && !keep -> View.GONE
        else -> throw IllegalStateException("Illegal state")
    }
}