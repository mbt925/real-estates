package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Decimal
import com.mbt925.realestate.core.decimal.Decimal.Companion.e
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Locale
import kotlin.test.assertEquals

data class BaseFormatterTestCase(
    val formatterBuilder: (
        locale: () -> Locale,
        digits: ((Decimal) -> Int)?,
    ) -> DecimalFormatter,
    val decimal: Decimal = 0L e 0,
    val locale: Locale,
    val digits: Int? = null,
    val expected: String = "",
) : FormatterTestCase {
    override fun test() {
        val digitsLambda = digits?.let {
            mockk<(Decimal) -> Int>().also {
                every { it(decimal) } returns digits
            }
        }
        val formatter = formatterBuilder(
            { locale },
            digitsLambda,
        )
        val actual = formatter.format(decimal)
        if (expected != actual) {
            println(
                """
                    [Formatter Test Failed]
                    given:
                        locale:     $locale
                        digits:     $digits 
                        decimal:    $decimal
                    expect: $expected
                    actual: $actual
            """.trimIndent()
            )
        }
        assertEquals(expected, actual)
        digitsLambda?.run {
            verify(exactly = 1) { digitsLambda(decimal) }
        }
    }
}
