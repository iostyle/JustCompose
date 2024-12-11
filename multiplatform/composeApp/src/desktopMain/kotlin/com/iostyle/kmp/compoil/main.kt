package com.iostyle.kmp.compoil

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.iostyle.kmp.compoil.ui.MenuBar
import com.iostyle.kmp.compoil.ui.Tray

fun main() = application {

    // 设置托盘（状态栏图标）
    Tray(this)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplatform-Oil",
    ) {
        // 设置菜单栏
        MenuBar(this@Window, this@application)

        App()
    }

}