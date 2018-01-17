package com.zetokz.currencyexchange.presentation.ui.rates.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.zetokz.currencyexchange.R
import com.zetokz.currencyexchange.presentation.base.adapter.BaseSimpleAdapterDelegate
import com.zetokz.currencyexchange.presentation.base.adapter.BaseViewHolder
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.model.Identifiable
import com.zetokz.currencyexchange.presentation.util.extension.bindView

/**
 * Created by Denys Nykyforov on 12/07/17.
 * Copyright © 2017. All rights reserved.
 */
internal class CurrencyRateAdapterDelegate :
    BaseSimpleAdapterDelegate<CurrencyRateAdapterDelegate.CurrencyRateViewHolder, CurrencyItem>() {

    override fun isForViewType(item: Identifiable, items: MutableList<Identifiable>, position: Int) =
        item is CurrencyItem

    override fun createViewHolder(itemView: View) = CurrencyRateViewHolder(itemView)

    override fun getItemResId() = R.layout.item_currency

    inner class CurrencyRateViewHolder(view: View) : BaseViewHolder<CurrencyItem>(view) {

        private val iconRateCountry: ImageView by bindView(R.id.icon_rate_country)
        private val textRateAbbreviation: TextView by bindView(R.id.text_rate_abbreviation)
        private val textRateDescription: TextView by bindView(R.id.text_rate_description)
        private val textRate: TextView by bindView(R.id.text_rate)

        override fun bind(item: CurrencyItem) {
            item.info?.let {
                iconRateCountry.setImageResource(it.iconRes)
                textRateDescription.setText(it.descriptionRes)
            }
            textRateAbbreviation.text = item.currencyName
            textRate.text = item.count.toString()
        }
    }
}