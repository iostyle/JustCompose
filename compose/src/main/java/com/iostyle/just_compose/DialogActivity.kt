package com.iostyle.just_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialogCompose()
        }
    }

    @Preview
    @Composable
    fun DialogCompose() {
        var show by remember { mutableStateOf(false) }
        Button(onClick = { show = show.not() }) {
            Text(text = "ShowDialog $show")
        }
        if (show) {
            AlertDialog(
                onDismissRequest = { show = false },
                confirmButton = {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = { show = false }) {
                        Text(text = "Dismiss")
                    }
                },
                title = { Text(text = "Dialog Title") },
                text = { Text(text = "Dialog Content") },
            )
        }
    }
}