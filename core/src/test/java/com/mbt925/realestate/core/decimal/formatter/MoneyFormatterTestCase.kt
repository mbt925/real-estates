package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Money
import com.mbt925.realestate.core.decimal.util.euroCents
import java.util.Locale
import kotlin.test.assertEquals

data class MoneyFormatterTestCase(
    val formatterBuilder: (
        locale: () -> Locale,
        smart: Boolean,
        sign: MoneySign,
    ) -> MoneyFormatter,
    val money: Money = 0L.euroCents,
    val locale: Locale,
    val smart: Boolean = true,
    val sign: MoneySign = MoneySign.None,
    val maxFractionDigits: Int? = null,
    val expected: String = "",
) : FormatterTestCase {
    override fun test() {
        val formatter = formatterBuilder(
            { locale },
            smart,
            sign,
        )
        val actual = formatter.format(money, maxFractionDigits)
        if (expected != actual) {
            println(
                """
                    [Formatter Test Failed]
                    given:
                        locale:     $locale
                        smart:      $smart 
                        money:      $money
                    expect: $expected
                    actual: $actual
            """.trimIndent()
            )
        }
        assertEquals(expected, actual)
    }
}
