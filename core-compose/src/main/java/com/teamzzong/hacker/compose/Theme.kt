package com.teamzzong.hacker.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class HackerColor(
    black: Color = HackerBlack,
    white: Color = HackerWhite,
    red: Color = HackerRed,
    lightGray: Color = HackerLightGray,
    darkGray: Color = HackerDarkGray,
    blue: Color = HackerBlue
) {
    var black by mutableStateOf(black)
        private set
    var white by mutableStateOf(white)
        private set
    var red by mutableStateOf(red)
        private set
    var lightGray by mutableStateOf(lightGray)
        private set
    var darkGray by mutableStateOf(darkGray)
        private set
    var blue by mutableStateOf(blue)
        private set
}

@Composable
fun HackerTheme(content: @Composable () -> Unit) {
    val colors = HackerColor()
    val typography = HackerTypography()
    CompositionLocalProvider(
        LocalHackerColors provides colors,
        LocalHackerTypography provides typography
    ) {
        MaterialTheme(content = content)
    }
}

object HackerTheme {
    val colors: HackerColor @Composable get() = LocalHackerColors.current
    val typography: HackerTypography @Composable get() = LocalHackerTypography.current
}

private val LocalHackerColors = staticCompositionLocalOf<HackerColor> {
    error("Any HackerColors Did Not Provided")
}

private val LocalHackerTypography = staticCompositionLocalOf<HackerTypography> {
    error("Any HackerTypography Did Not Provided")
}
