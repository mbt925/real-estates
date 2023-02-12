package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

class NumberFormatter(
    private val locale: () -> Locale,
    private val digits: (Decimal.() -> Int)?,
) : DecimalFormatter {

    override fun format(decimal: Decimal): String {
        val digits = digits?.invoke(decimal)
        val smartMinDigits = digits ?: 0
        val smartMaxDigits = digits ?: run {
            when {
                decimal.factor == 0L || decimal.baseTenExponent >= 0 -> 0
                else -> abs(decimal.baseTenExponent)
            }
        }

        return NumberFormat.getNumberInstance(locale()).format(
            minDigits = smartMinDigits,
            maxDigits = smartMaxDigits,
            decimal = decimal,
        )
    }

    override fun format(
        value: Double,
        decimalPoints: Int,
    ): String {
        return NumberFormat.getNumberInstance(locale()).format(
            minDigits = 0,
            maxDigits = decimalPoints,
            value = value,
        )
    }
}
