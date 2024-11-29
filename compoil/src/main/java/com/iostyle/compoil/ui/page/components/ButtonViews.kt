package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun TopBarAddButton(
    modifier: Modifier = Modifier, actions: PageActions
) {
    val fragmentManager = getFragmentManager()
    Button(
        modifier = modifier.size(30.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = { fragmentManager?.let { actions.onFloatingButtonClick(it) } }) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = "+",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun TopBarAddButtonPreview(
) {
    TopBarAddButton(Modifier, PageActions())
}