package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal
import com.mbt925.realestate.core.decimal.Money
import com.mbt925.realestate.core.decimal.util.decimalPlaces
import com.mbt925.realestate.core.decimal.util.negative
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.math.min

class MoneyFormatter(
    private val locale: () -> Locale,
    private val smart: Boolean,
    private val sign: MoneySign,
) {

    companion object {
        internal val minusSignRegex = "[-−]".toRegex() // U+002D, U+2212
        fun Money.format(formatter: MoneyFormatter): String = formatter.format(this)
    }

    fun format(
        value: Money,
        maxFractionDigits: Int? = null,
    ) = buildString {
        val sign = sign.invoke(value)?.toString().orEmpty()
        val decimalFormat =
            (NumberFormat.getCurrencyInstance(locale()) as DecimalFormat)
                .apply {
                    currency = Currency.getInstance(value.currencyCode)
                    negativePrefix = negativePrefix.replace(minusSignRegex, sign)
                    negativeSuffix = negativeSuffix.replace(minusSignRegex, sign)
                }

        decimalFormat
            .format(
                value = value,
                smart = smart,
                maxFractionDigits = maxFractionDigits,
            ).let(::append)
    }

}

internal val Money.hasZeroDecimals: Boolean
    get() {
        var num = minorUnit.absoluteValue

        var remainingDecimalPlaces = decimalPlaces
        while (num >= 0 && remainingDecimalPlaces > 0) {
            val isZero = num.mod(10) == 0
            if (!isZero) return false

            num /= 10
            remainingDecimalPlaces -= 1
        }
        return true
    }

internal fun DecimalFormat.format(
    value: Money,
    smart: Boolean,
    maxFractionDigits: Int?,
): String {

    val fractionDigits = currency.defaultFractionDigits
    val decimals = if (smart && value.hasZeroDecimals) 0
    else min(fractionDigits, maxFractionDigits ?: fractionDigits)

    return format(
        minDigits = decimals,
        maxDigits = decimals,
        decimal = Decimal(value.negative.minorUnit, -fractionDigits)
    )
}
