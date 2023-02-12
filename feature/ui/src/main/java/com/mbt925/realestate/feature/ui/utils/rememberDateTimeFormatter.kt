package com.mbt925.realestate.feature.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun rememberDateTimeFormatter(): DateTimeFormatter {
    return remember { DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT) }
}
