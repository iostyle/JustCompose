package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iostyle.compoil.R

@Composable
fun SwipeToDismissItem(onDismiss: @Composable () -> Unit, backgroundContent: @Composable () -> Unit) {
    val state = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = state,
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(modifier = Modifier.size(30.dp), painter = painterResource(R.drawable.ic_remove), contentDescription = "Remove")
            }
        }, Modifier.fillMaxWidth(),
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