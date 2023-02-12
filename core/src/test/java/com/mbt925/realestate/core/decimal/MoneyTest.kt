package com.mbt925.realestate.core.decimal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class MoneyTest {

    @Test
    fun compareTo_valid() {
        val money1 = Money(1001, Euro.currencyCode)
        val money2 = Money(1001, Euro.currencyCode)
        assertEquals(money1, money2)

        val money3 = Money(1001, "YEN")
        val money4 = Money(101, "YEN")
        assertNotEquals(money3, money4)
    }

    @Test
    fun compareTo_invalid() {
        assertFailsWith(RuntimeException::class) {
            val money1 = Money(1001, Euro.currencyCode)
            val money2 = Money(1001, "USD")

            money1.compareTo(money2)
        }
    }

    @Test
    fun plus_valid() {
        val money1 = Money(1001, "USD")
        val money2 = Money(1001, "USD")

        assertEquals(Money(2002, "USD"), money1 + money2)
    }

    @Test
    fun plus_invalid() {
        assertFailsWith(RuntimeException::class) {
            val money1 = Money(1001, Euro.currencyCode)
            val money2 = Money(1001, "USD")

            money1.plus(money2)
        }
    }

    @Test
    fun minus_valid() {
        val money1 = Money(2001, "YEN")
        val money2 = Money(1001, "YEN")

        assertEquals(Money(1000, "YEN"), money1 - money2)
    }

    @Test
    fun minus_invalid() {
        assertFailsWith(RuntimeException::class) {
            val money1 = Money(1001, Euro.currencyCode)
            val money2 = Money(1001, "USD")

            money1.minus(money2)
        }
    }

}
