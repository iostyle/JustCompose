package com.iostyle.kmp.compoil.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.rememberTrayState

@Composable
fun Tray(scope: ApplicationScope) {
    val trayState = rememberTrayState()
    val trayIcon = painterResource("ic_apple.svg")

    scope.Tray(state = trayState, icon = trayIcon, menu = {
        Item("Exit", onClick = { scope.exitApplication() })
    })
}