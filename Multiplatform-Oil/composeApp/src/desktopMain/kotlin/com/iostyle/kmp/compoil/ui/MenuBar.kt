package com.iostyle.kmp.compoil.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

@Composable
fun MenuBar(scope: FrameWindowScope, applicationScope: ApplicationScope) {
    var itemChecked by remember { mutableStateOf(false) }
    scope.MenuBar {
        Menu("菜单") {
            Menu("选项一") {
                Item("子项一") {}
                Item("子项一") { applicationScope.exitApplication() }
                CheckboxItem("选中", itemChecked) {
                    itemChecked = it
                }
            }
            Item("选项二") {
                applicationScope.exitApplication()
            }
        }
    }
}