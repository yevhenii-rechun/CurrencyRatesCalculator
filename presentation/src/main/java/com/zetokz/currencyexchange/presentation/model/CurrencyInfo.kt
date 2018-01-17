package com.zetokz.currencyexchange.presentation.model

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.zetokz.currencyexchange.R

/**
 * Created by Yevhenii Rechun on 1/18/18.
 * Copyright © 2017. All rights reserved.
 */
enum class CurrencyInfo(
    val rawName: String,
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int
) {

    AUD("AUD", R.string.currency_aud, R.drawable.ic_aud_au),
    BGN("BGN", R.string.currency_bgn, R.drawable.ic_bgn_bg),
    BRL("BRL", R.string.currency_brl, R.drawable.ic_bgn_bg), //todo add icon for this currency
    CAD("CAD", R.string.currency_cad, R.drawable.ic_cad_ca),
    CHF("CHF", R.string.currency_chf, R.drawable.ic_chf_ch),
    CNY("CNY", R.string.currency_cny, R.drawable.ic_cny_cn),
    CZK("CZK", R.string.currency_czk, R.drawable.ic_czk_cz),
    DKK("DKK", R.string.currency_dkk, R.drawable.ic_dkk_dk),
    GBP("GBP", R.string.currency_gbp, R.drawable.ic_gbp_gb),
    HKD("HKD", R.string.currency_hkd, R.drawable.ic_hkd_hk),
    HRK("HRK", R.string.currency_hrk, R.drawable.ic_hrk_hr),
    HUF("HUF", R.string.currency_huf, R.drawable.ic_huf_hu),
    IDR("IDR", R.string.currency_idr, R.drawable.ic_idr_id),
    ILS("ILS", R.string.currency_ils, R.drawable.ic_ils_il),
    INR("INR", R.string.currency_inr, R.drawable.ic_inr_in),
    JPY("JPY", R.string.currency_jpy, R.drawable.ic_jpy_jp),
    KRW("KRW", R.string.currency_krw, R.drawable.ic_krw_kr),
    MXN("MXN", R.string.currency_mxn, R.drawable.ic_mxn_mx),
    MYR("MYR", R.string.currency_myr, R.drawable.ic_myr_my),
    NOK("NOK", R.string.currency_nok, R.drawable.ic_nok_no),
    NZD("NZD", R.string.currency_nzd, R.drawable.ic_nzd_nz),
    PHP("PHP", R.string.currency_php, R.drawable.ic_php_ph),
    PLN("PLN", R.string.currency_pln, R.drawable.ic_pln_pl),
    RON("RON", R.string.currency_ron, R.drawable.ic_ron_ro),
    RUB("RUB", R.string.currency_rub, R.drawable.ic_rub_ru),
    SEK("SEK", R.string.currency_sek, R.drawable.ic_sek_se),
    SGD("SGD", R.string.currency_sgd, R.drawable.ic_sgd_sg),
    THB("THB", R.string.currency_thb, R.drawable.ic_thb_th),
    TRY("TRY", R.string.currency_try, R.drawable.ic_try_tr),
    ZAR("ZAR", R.string.currency_zar, R.drawable.ic_zar_za),
    EUR("EUR", R.string.currency_eur, R.drawable.ic_usd_us),//todo add icon for this currency
    USD("USD", R.string.currency_usd, R.drawable.ic_usd_us);//todo add icon for this currency

    companion object {

        private val CURRENCIES_BY_KEY: Map<String, CurrencyInfo> by lazy { getAll() }

        private fun getAll(): Map<String, CurrencyInfo> {
            val all = mutableMapOf<String, CurrencyInfo>()
            values().asSequence().forEach { all[it.rawName] = it }
            return all.toMap()
        }

        fun getInfoByKey(currencyKey: String): CurrencyInfo? = CURRENCIES_BY_KEY[currencyKey]
    }
}