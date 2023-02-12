package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal
import java.text.NumberFormat

fun NumberFormat.format(
    minDigits: Int,
    maxDigits: Int,
    decimal: Decimal,
): String = apply {
    minimumFractionDigits = minDigits
    maximumFractionDigits = maxDigits
}.format(decimal.factor.toBigDecimal().movePointRight(decimal.baseTenExponent))

fun NumberFormat.format(
    minDigits: Int,
    maxDigits: Int,
    value: Double,
): String = apply {
    minimumFractionDigits = minDigits
    maximumFractionDigits = maxDigits
}.format(value.toBigDecimal())
