package com.iostyle.kmp.compoil

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplatform-Oil",
    ) {
        App()
    }

    val trayState = rememberTrayState()
    val trayIcon = painterResource("ic_apple.svg")

    // 设置托盘（状态栏图标）
    Tray(state = trayState, icon = trayIcon, menu = {
        Item("Exit", onClick = ::exitApplication)
    })
}