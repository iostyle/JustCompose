package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SwipeToDismissItem(onDismiss: @Composable () -> Unit, backgroundContent: @Composable () -> Unit) {
    val state = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = state,
        {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Text(text = "侧滑删除")
            }
        }, Modifier
            .fillMaxWidth()
            .background(Color.Red),
        enableDismissFromStartToEnd = false
    )
    {
        backgroundContent()
    }
    when (state.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDismiss()
        }

        else -> {
        }
    }
}