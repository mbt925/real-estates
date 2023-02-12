package com.mbt925.realestate.feature.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import com.mbt925.realestate.core.decimal.formatter.MoneyFormatter
import com.mbt925.realestate.core.decimal.formatter.MoneySign

@Composable
fun rememberMoneyFormatter(
    smart: Boolean = true,
    sign: MoneySign = MoneySign.Natural,
): MoneyFormatter {

    val locale = Locale.current
    return remember(locale, smart, sign) {
        MoneyFormatter(
            locale = { locale.asJavaLocale() },
            smart = smart,
            sign = sign
        )
    }
}

fun Locale.asJavaLocale(): java.util.Locale = java.util.Locale.forLanguageTag(toLanguageTag())
