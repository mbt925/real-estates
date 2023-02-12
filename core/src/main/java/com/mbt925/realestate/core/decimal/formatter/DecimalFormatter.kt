package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal

interface DecimalFormatter {
    fun format(decimal: Decimal): String
    fun format(value: Double, decimalPoints: Int): String
}
