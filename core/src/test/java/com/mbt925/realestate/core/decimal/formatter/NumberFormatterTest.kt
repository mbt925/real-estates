package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal.Companion.e
import java.util.Locale
import kotlin.test.Test

class NumberFormatterTest {

    private val testCase =
        BaseFormatterTestCase(formatterBuilder = ::NumberFormatter, locale = Locale.US)

    @Test
    fun onVariousCombinations() {
        listOf(
            testCase.copy(
                decimal = 0L e 0, locale = Locale.US,
                digits = 0,
                expected = "0",
            ),
            testCase.copy(
                decimal = 0L e 0, locale = Locale.US,
                digits = 2,
                expected = "0.00",
            ),
            testCase.copy(
                decimal = 1234567L e 0,
                locale = Locale.US,
                expected = "1,234,567",
            ),
            testCase.copy(
                decimal = 1234567L e 0,
                locale = Locale.US,
                digits = 2,
                expected = "1,234,567.00",
            ),
            testCase.copy(
                decimal = 0L e 0,
                locale = Locale.GERMANY,
                digits = 0,
                expected = "0",
            ),
            testCase.copy(
                decimal = 0L e 0,
                locale = Locale.GERMANY,
                expected = "0",
            ),
            testCase.copy(
                decimal = 0L e 0,
                locale = Locale.GERMANY,
                digits = 2,
                expected = "0,00",
            ),
            testCase.copy(
                decimal = 1234567L e -4,
                locale = Locale.GERMANY,
                expected = "123,4567",
            ),
            testCase.copy(
                decimal = 123456789L e -4,
                locale = Locale.GERMANY,
                expected = "12.345,6789",
            ),
            testCase.copy(
                decimal = 123456789L e -4,
                locale = Locale.GERMANY,
                digits = 2,
                expected = "12.345,68",
            ),
            testCase.copy(
                decimal = 1234567L e 0,
                locale = Locale.GERMANY,
                digits = 2,
                expected = "1.234.567,00",
            ),
        ).forEach(BaseFormatterTestCase::test)
    }
}
