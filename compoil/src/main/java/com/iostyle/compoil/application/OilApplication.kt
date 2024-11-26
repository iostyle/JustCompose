package com.iostyle.compoil.application

import android.app.Application
import com.iostyle.compoil.ui.theme.Theme
import com.iostyle.compoil.ui.theme.currentThemeMode
import com.tencent.mmkv.MMKV

class OilApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initMMKV()
        initTheme()
    }

    private fun initMMKV() {
        MMKV.initialize(this).also {
            System.out.println("mmkv root: " + it)
        }
    }

    private fun initTheme() {
        currentThemeMode = MMKV.defaultMMKV().decodeParcelable("mmkv_theme", Theme::class.java)
    }

}