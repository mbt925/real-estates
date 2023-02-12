package com.mbt925.realestate.core.decimal

data class Money(
    val minorUnit: Long,
    val currencyCode: String,
) {
    operator fun compareTo(other: Money): Int {
        if (currencyCode != other.currencyCode) {
            throw RuntimeException(
                "Cannot compare amounts with different currencies: $currencyCode != ${other.currencyCode}")
        }

        return minorUnit.compareTo(other.minorUnit)
    }

    operator fun plus(other: Money): Money {
        if (currencyCode != other.currencyCode) {
            throw RuntimeException(
                "Cannot add moneys with different currencies: $currencyCode != ${other.currencyCode}")
        }

        return Money(
            minorUnit = minorUnit + other.minorUnit,
            currencyCode = currencyCode,
        )
    }

    operator fun minus(other: Money): Money {
        if (currencyCode != other.currencyCode) {
            throw RuntimeException(
                "Cannot subtract moneys with different currencies: $currencyCode != ${other.currencyCode}")
        }

        return Money(
            minorUnit = minorUnit - other.minorUnit,
            currencyCode = currencyCode,
        )
    }
}
