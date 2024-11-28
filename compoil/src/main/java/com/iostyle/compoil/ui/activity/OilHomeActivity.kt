package com.iostyle.compoil.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.iostyle.compoil.data.Records
import com.iostyle.compoil.ui.page.PageRoute
import com.iostyle.compoil.ui.theme.JustComposeTheme

class OilHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustComposeTheme {
                PageRoute()
            }
        }
    }
}