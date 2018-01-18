package com.zetokz.currencyexchange.presentation.ui.rates

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zetokz.currencyexchange.R
import com.zetokz.currencyexchange.presentation.base.BaseActivity
import com.zetokz.currencyexchange.presentation.model.CurrencyInputItem
import com.zetokz.currencyexchange.presentation.model.CurrencyItem
import com.zetokz.currencyexchange.presentation.ui.rates.adapter.CurrencyRateAdapter
import com.zetokz.currencyexchange.presentation.util.SimpleTextWatcher
import com.zetokz.currencyexchange.presentation.util.extension.bindView
import com.zetokz.currencyexchange.presentation.util.extension.changeVisibility
import com.zetokz.currencyexchange.presentation.util.list.SpacingItemDecoration
import com.zetokz.currencyexchange.presentation.widget.FixedMessageView
import javax.inject.Inject

class CurrencyRatesActivity : BaseActivity(), CurrencyRatesView {

    private val progress: ProgressBar by bindView(R.id.progress)
    private val viewOutdated: FixedMessageView by bindView(R.id.view_outdated)
    private val iconRateCountry: ImageView by bindView(R.id.icon_rate_country)
    private val textRateAbbreviation: TextView by bindView(R.id.text_rate_abbreviation)
    private val textRateDescription: TextView by bindView(R.id.text_rate_description)
    private val inputValue: EditText by bindView(R.id.input_value)
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
        progress.changeVisibility(false)
        listCurrencyRates.changeVisibility(true)
        currencyRatesAdapter.dispatchNewItems(currencyItems)
    }

    override fun showBase(currencyInputItem: CurrencyInputItem) {
        with(currencyInputItem) {
            inputValue.setText(count.toString())
            textRateAbbreviation.text = currencyName
            info?.let {
                textRateDescription.setText(it.descriptionRes)
                iconRateCountry.setImageResource(it.iconRes)
            }
        }
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
        inputValue.addTextChangedListener(SimpleTextWatcher(afterTextChangedAction = {
            presenter.onMainCountChanged(it.toString().toDouble())
        }))
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "Rates & Conversion"
    }
}