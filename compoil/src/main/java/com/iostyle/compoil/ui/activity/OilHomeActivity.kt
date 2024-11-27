package com.iostyle.compoil.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.iostyle.compoil.bean.Records
import com.iostyle.compoil.ui.page.PageRoute
import com.iostyle.compoil.ui.theme.JustComposeTheme

class OilHomeActivity : AppCompatActivity() {

    private val recordsList: MutableList<Records> by lazy { mutableListOf() }

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