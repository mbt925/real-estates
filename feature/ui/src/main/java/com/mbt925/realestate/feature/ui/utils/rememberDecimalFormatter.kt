package com.mbt925.realestate.feature.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import com.mbt925.realestate.core.decimal.Decimal
import com.mbt925.realestate.core.decimal.formatter.DecimalFormatter
import com.mbt925.realestate.core.decimal.formatter.NumberFormatter

@Composable
fun rememberDecimalFormatter(
    digits: (Decimal.() -> Int)? = null,
): DecimalFormatter {

    val locale = Locale.current
    return remember(locale) {
        NumberFormatter(
            locale = { locale.asJavaLocale() },
            digits = digits,
        )
    }
}
