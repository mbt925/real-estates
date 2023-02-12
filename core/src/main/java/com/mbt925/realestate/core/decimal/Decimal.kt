package com.mbt925.realestate.core.decimal

import com.mbt925.realestate.core.decimal.formatter.DecimalFormatter

data class Decimal(
    val factor: Long,
    val baseTenExponent: Int,
) {
    companion object {
        infix fun Long.e(value: Int): Decimal = Decimal(factor = this, baseTenExponent = value)
        fun Decimal.format(formatter: DecimalFormatter): String = formatter.format(this)
    }
}
