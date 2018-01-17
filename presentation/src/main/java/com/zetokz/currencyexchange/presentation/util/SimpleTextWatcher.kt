package com.zetokz.currencyexchange.presentation.util

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Denys Nykyforov on 31.10.2017
 * Copyright (c) 2017. All right reserved
 */
class SimpleTextWatcher(
    private val beforeTextChangedAction: SimpleTextWatcher.(s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    private val onTextChangedAction: SimpleTextWatcher.(s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    private val afterTextChangedAction: SimpleTextWatcher.(s: Editable?) -> Unit = { _ -> }
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChangedAction(this, s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChangedAction(this, s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChangedAction(this, s)
    }
}