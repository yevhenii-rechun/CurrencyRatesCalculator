package com.zetokz.currencyexchange.presentation.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.zetokz.currencyexchange.presentation.model.Identifiable

/**
 * Created by Yevhenii Rechun on 1/17/18.
 * Copyright © 2017. All rights reserved.
 */
abstract class BaseViewHolder<in I : Identifiable>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: I)
}