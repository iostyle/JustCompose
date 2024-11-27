package com.iostyle.compoil.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentManager

@Composable
fun getFragmentManager(): FragmentManager? {
    return when (LocalContext.current) {
        is AppCompatActivity -> {
            (LocalContext.current as AppCompatActivity).supportFragmentManager
        }
        else -> null
    }
}