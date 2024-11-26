package com.iostyle.compoil.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

/**
 *  此后我们约定色值成对儿出现
 *  @param Pair.first 为浅色模式色值
 *  @param Pair.second 为深色模式色值
 *
 *  ----------------------------------------------------------------------------------------
 */

@Composable
fun getThemeColor(color: Pair<Color, Color>): Color {
    return if (isInDarkTheme()) color.second else color.first
}

@Composable
fun Pair<Color, Color>.get(): Color {
    return getThemeColor(this)
}

val Pair<Color, Color>.color: Color
    @Composable get() {
        return this.get()
    }

@Composable
fun Long.get(): Color {
    return Color(this)
}

val Long.color: Color
    @Composable get() {
        return this.get()
    }

private infix fun Long.x(dark: Long): Pair<Color, Color> {
    return Pair(Color(this), Color(dark))
}

/**
 * Color Pair
 * ----------------------------------------------------------------------------------------
 */

val Text = 0xFF000000 x 0xFFFFFFFF

val TiktokCyan = 0xFF24f4ee
val TiktokRed = 0xFFfe1955

