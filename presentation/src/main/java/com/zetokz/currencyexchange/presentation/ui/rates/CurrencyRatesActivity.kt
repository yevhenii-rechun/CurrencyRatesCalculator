package com.zetokz.currencyexchange.presentation.ui.rates

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zetokz.currencyexchange.R
import com.zetokz.currencyexchange.presentation.base.BaseActivity
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.ui.rates.adapter.CurrencyRateAdapter
import com.zetokz.currencyexchange.presentation.util.extension.bindView
import com.zetokz.currencyexchange.presentation.util.list.SpacingItemDecoration
import javax.inject.Inject

class CurrencyRatesActivity : BaseActivity(), CurrencyRatesView {

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val listCurrencyRates: RecyclerView by bindView(R.id.list_currency_rates)

    private lateinit var currencyRatesAdapter: CurrencyRateAdapter

    @InjectPresenter @Inject
    internal lateinit var presenter: CurrencyRatesPresenter

    @ProvidePresenter internal fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_rates)

        initToolbar()
        initAdapter()
        initViews()
    }

    override fun showCurrencies(currencyItems: List<CurrencyItem>) {
        currencyRatesAdapter.dispatchNewItems(currencyItems)
    }

    override fun showBase(currencyInputItem: CurrencyInputItem) {
        println("Not implemented") /*TODO("not implemented")*/
    }

    private fun initAdapter() {
        currencyRatesAdapter = CurrencyRateAdapter(presenter::onCurrencyClicked)
    }

    private fun initViews() {
        listCurrencyRates.apply {
            adapter = currencyRatesAdapter
            addItemDecoration(SpacingItemDecoration.create {
                spaceSize = context.resources.getDimensionPixelSize(R.dimen.normal)
                showOnSides = true
            })
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "Rates & Conversion"
    }
}