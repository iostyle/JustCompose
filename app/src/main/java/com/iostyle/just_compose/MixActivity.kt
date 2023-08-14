package com.iostyle.just_compose

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

class MixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mix)

        // 在 View 中使用 Compose
        findViewById<ComposeView>(R.id.compose).setContent {
            CustomText()
        }


    }

    @Preview
    @Composable
    fun CustomText() {
        var name by remember { mutableStateOf("Name") }
        Column {
            Text(text = "TestText", Modifier.clickable { name += "哈" })
            // 在 Compose 中使用 View
            AndroidView(factory = {
                TextView(it).apply {
                    text = "Android Text View"
                }
            }) {
                // mutable 更新
                it.text = name
            }
        }
    }
}