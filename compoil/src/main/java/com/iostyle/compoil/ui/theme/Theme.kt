package com.iostyle.compoil.ui.theme

import android.os.Build
import android.os.Parcelable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.parcelize.Parcelize

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Parcelize
enum class Theme : Parcelable {
    THEME_MODE_SYSTEM,
    THEME_MODE_LIGHT,
    THEME_MODE_DARK
}

// TODO setting
var currentThemeMode: Theme? = null

@Composable
fun isInDarkTheme(): Boolean {
    return when (currentThemeMode) {
        null, Theme.THEME_MODE_SYSTEM -> {
            isSystemInDarkTheme()
        }

        Theme.THEME_MODE_LIGHT -> {
            false
        }

        Theme.THEME_MODE_DARK -> {
            true
        }
    }
}

@Composable
fun JustComposeTheme(
    darkTheme: Boolean = isInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}