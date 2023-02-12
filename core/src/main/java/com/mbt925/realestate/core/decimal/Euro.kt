package com.mbt925.realestate.core.decimal

import java.util.Currency

object Euro {
    const val currencyCode = "EUR"
    val currency: Currency
        get() = Currency.getInstance(currencyCode)

}
