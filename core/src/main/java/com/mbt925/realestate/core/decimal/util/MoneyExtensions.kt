package com.mbt925.realestate.core.decimal.util

import com.mbt925.realestate.core.decimal.Euro
import com.mbt925.realestate.core.decimal.Money
import java.util.Currency
import kotlin.math.absoluteValue

val Money.negative get() = copy(minorUnit = -minorUnit.absoluteValue)
val Long.euroCents get() = Money(this, Euro.currencyCode)
fun Long.currency(code: String): Money = Money(minorUnit = this, currencyCode = code)
val Double.euroCents get() = Money((this * 100).toLong(), Euro.currencyCode)

val Money.decimalPlaces: Int
    get() = currency.defaultFractionDigits

val Money.currency: Currency get() = Currency.getInstance(currencyCode)
