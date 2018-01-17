package com.zetokz.currencyexchange.presentation.ui.rates.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.zetokz.currencyexchange.R
import com.zetokz.currencyexchange.presentation.base.adapter.BaseSimpleAdapterDelegate
import com.zetokz.currencyexchange.presentation.base.adapter.BaseViewHolder
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.Identifiable
import com.zetokz.currencyexchange.presentation.util.SimpleTextWatcher
import com.zetokz.currencyexchange.presentation.util.extension.bindView
import java.math.BigDecimal

/**
 * Created by Denys Nykyforov on 12/07/17.
 * Copyright © 2017. All rights reserved.
 */
internal class CurrencyRateInputAdapterDelegate(
    private val inputChangedAction: (CurrencyInputItem) -> Unit
) : BaseSimpleAdapterDelegate<CurrencyRateInputAdapterDelegate.CurrencyRateInputViewHolder, CurrencyInputItem>() {

    init {
        useClickListenerForRootItem = false
    }

    override fun isForViewType(item: Identifiable, items: MutableList<Identifiable>, position: Int) =
        item is CurrencyInputItem

    override fun createViewHolder(itemView: View) = CurrencyRateInputViewHolder(itemView)

    override fun getItemResId() = R.layout.item_currency_input

    inner class CurrencyRateInputViewHolder(view: View) : BaseViewHolder<CurrencyInputItem>(view) {

        private val iconRateCountry: ImageView by bindView(R.id.icon_rate_country)
        private val textRateAbbreviation: TextView by bindView(R.id.text_rate_abbreviation)
        private val textRateDescription: TextView by bindView(R.id.text_rate_description)
        private val inputValue: EditText by bindView(R.id.input_value)

        override fun bind(item: CurrencyInputItem) {
//            iconRateCountry.setImageDrawable() //todo: Find icons for currencies
            textRateAbbreviation.text = item.currencyName
            inputValue.setText(item.count.toString())
            inputValue.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    inputValue.removeTextChangedListener(this)
                    s.let { inputChangedAction.invoke(item.copy(count = BigDecimal(it.toString()))) }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            inputValue.addTextChangedListener(SimpleTextWatcher(afterTextChangedAction = {
                inputChangedAction.invoke(item.copy(count = BigDecimal(it.toString())))
            }))
        }
    }
}