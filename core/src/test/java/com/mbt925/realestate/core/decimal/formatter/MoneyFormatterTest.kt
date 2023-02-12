package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Euro
import com.mbt925.realestate.core.decimal.formatter.MoneySign.None
import com.mbt925.realestate.core.decimal.util.currency
import java.util.Locale
import kotlin.test.Test

class MoneyFormatterTest {

    @Test
    fun onEuro() {
        onUsLocale(Euro.currencyCode, "€", false)
        onGermanLocale(Euro.currencyCode, "€", false)
        onNlLocale(Euro.currencyCode, "€", false)
    }

    @Test
    fun onDollar() {
        onUsLocale("USD", "$", false)
        onGermanLocale("USD", "$", false)
        onNlLocale("USD", "US$", false)
    }

    @Test
    fun onYen() {
        onUsLocale("JPY", "¥", true)
        onGermanLocale("JPY", "¥", true)
        onNlLocale("JPY", "JP¥", true)
    }

    private fun onUsLocale(
        code: String,
        symbol: String,
        noFraction: Boolean,
    ) {
        val testCase =
            MoneyFormatterTestCase(formatterBuilder = ::MoneyFormatter, locale = Locale.US)

        listOf(
            testCase.copy(money = 0L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol}0" else "${symbol}0.00"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = "${symbol}0"),
            testCase.copy(money = (-1L).currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "-${symbol}1" else "-${symbol}0.01"),
            testCase.copy(money = (-100L).currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "-${symbol}100" else "-${symbol}1.00"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = if (noFraction) "-${symbol}100" else "-${symbol}1"),
            testCase.copy(money = (-1234567L).currency(code), smart = true,
                sign = MoneySign.ForceSign,
                expected = if (noFraction) "-${symbol}1,234,567" else "-${symbol}12,345.67"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = None,
                expected = "${symbol}0"),
            testCase.copy(money = 0L.currency(code), smart = false, sign = None,
                expected = if (noFraction) "${symbol}0" else "${symbol}0.00"),
            testCase.copy(money = 1234567L.currency(code), smart = false, sign = None,
                maxFractionDigits = 0,
                expected = if (noFraction) "${symbol}1,234,567" else "${symbol}12,346"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.Natural,
                expected = "${symbol}0"),
            testCase.copy(money = (-100L).currency(code), smart = false, sign = MoneySign.Natural,
                expected = if (noFraction) "-${symbol}100" else "-${symbol}1.00"),
            testCase.copy(money = 123456700L.currency(code), smart = true, sign = MoneySign.Natural,
                expected = if (noFraction) "${symbol}123,456,700" else "${symbol}1,234,567"),
        ).forEach(MoneyFormatterTestCase::test)
    }

    private fun onGermanLocale(
        code: String,
        symbol: String,
        noFraction: Boolean,
    ) {
        val testCase =
            MoneyFormatterTestCase(formatterBuilder = ::MoneyFormatter, locale = Locale.GERMANY)

        listOf(
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = "0\u00A0${symbol}"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = if (noFraction) "-100\u00A0${symbol}" else "-1\u00A0${symbol}"),
            testCase.copy(money = 200L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "+200\u00A0${symbol}" else "+2,00\u00A0${symbol}"),
            testCase.copy(money = 0L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "0\u00A0${symbol}" else "0,00\u00A0${symbol}"),
            testCase.copy(money = (-1L).currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "-1\u00A0${symbol}" else "-0,01\u00A0${symbol}"),
            testCase.copy(money = 2L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "+2\u00A0${symbol}" else "+0,02\u00A0${symbol}"),
            testCase.copy(money = (-1234567L).currency(code), smart = false,
                sign = MoneySign.ForceSign,
                expected = if (noFraction) "-1.234.567 ${symbol}" else "-12.345,67 $symbol"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = None,
                expected = "0\u00A0${symbol}"),
            testCase.copy(money = 123456700L.currency(code), smart = true, sign = None,
                expected = if (noFraction) "123.456.700 ${symbol}" else "1.234.567 $symbol"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.Natural,
                expected = "0\u00A0${symbol}"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.Natural,
                expected = if (noFraction) "-100\u00A0${symbol}" else "-1\u00A0${symbol}"),
            testCase.copy(money = 200L.currency(code), smart = false, sign = MoneySign.Natural,
                maxFractionDigits = 0,
                expected = if (noFraction) "200\u00A0${symbol}" else "2\u00A0${symbol}"),
            testCase.copy(money = 123456700L.currency(code), smart = false,
                sign = MoneySign.Natural,
                expected = if (noFraction) "123.456.700 ${symbol}" else "1.234.567,00 $symbol"),
        ).forEach(MoneyFormatterTestCase::test)
    }

    private fun onNlLocale(
        code: String,
        symbol: String,
        noFraction: Boolean,
    ) {
        val testCase =
            MoneyFormatterTestCase(formatterBuilder = ::MoneyFormatter, locale = Locale("nl", "NL"))

        listOf(
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = "${symbol} 0"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol} -100" else "${symbol} -1"),
            testCase.copy(money = 200L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol} +200" else "${symbol} +2,00"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = "${symbol} 0"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol} -100" else "${symbol} -1"),
            testCase.copy(money = 200L.currency(code), smart = false, sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol} +200" else "${symbol} +2,00"),
            testCase.copy(money = (-1234567L).currency(code), smart = false,
                sign = MoneySign.ForceSign,
                expected = if (noFraction) "${symbol} -1.234.567" else "${symbol} -12.345,67"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = None,
                expected = "${symbol} 0"),
            testCase.copy(money = 0L.currency(code), smart = false, sign = None,
                maxFractionDigits = 0,
                expected = if (noFraction) "${symbol} 0" else "${symbol} 0"),
            testCase.copy(money = 123456700L.currency(code), smart = false, sign = None,
                expected = if (noFraction) "${symbol} 123.456.700" else "${symbol} 1.234.567,00"),
            testCase.copy(money = 0L.currency(code), smart = true, sign = MoneySign.Natural,
                expected = "${symbol} 0"),
            testCase.copy(money = (-100L).currency(code), smart = true, sign = MoneySign.Natural,
                expected = if (noFraction) "${symbol} -100" else "${symbol} -1"),
            testCase.copy(money = 2L.currency(code), smart = false, sign = MoneySign.Natural,
                expected = if (noFraction) "${symbol} 2" else "${symbol} 0,02"),
            testCase.copy(money = 123456700L.currency(code), smart = false,
                sign = MoneySign.Natural,
                expected = if (noFraction) "${symbol} 123.456.700" else "${symbol} 1.234.567,00"),
        ).forEach(MoneyFormatterTestCase::test)
    }
}
