package com.mbt925.realestate.core.decimal.formatter

import com.mbt925.realestate.core.decimal.Money

enum class MoneySign(val invoke: Money.() -> Char?) {

    None({ null }),
    Natural({ if (minorUnit < 0) '-' else null }),
    ForceSign({
        when {
            minorUnit > 0 -> '+'
            minorUnit < 0 -> '-'
            else -> null
        }
    }),

}
