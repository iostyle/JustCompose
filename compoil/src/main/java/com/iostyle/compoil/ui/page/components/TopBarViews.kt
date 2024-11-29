package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .statusBarsPadding()
            .height(45.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier, text = "Compoil", fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer, fontFamily = FontFamily.Monospace
        )
    }
}