package com.iostyle.compoil.ui.page.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iostyle.compoil.ext.getFragmentManager
import com.iostyle.compoil.ui.page.PageActions
import com.iostyle.compoil.ui.page.PageState

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier, state: PageState, actions: PageActions
) {
    val fragmentManager = getFragmentManager()
    Button(
        modifier = modifier, onClick = { fragmentManager?.let { actions.onFloatingButtonClick(it) } }) {
        Text(text = "Add")
    }
}