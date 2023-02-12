package com.mbt925.realestate.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Blue700,
    secondary = Green600,
    secondaryVariant = GREY700,
    surface = Green900,
    background = GREY900
)

private val LightColorPalette = lightColors(
    primary = Blue300,
    secondary = Green600,
    secondaryVariant = GREY300,
    surface = Green100,
    background = Color.White,
)

@Composable
fun RealEstatesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
