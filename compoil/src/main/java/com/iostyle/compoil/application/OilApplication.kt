package com.iostyle.compoil.application

import android.app.Application
import com.iostyle.compoil.ui.theme.Theme
import com.iostyle.compoil.ui.theme.currentThemeMode
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class OilApplication : Application() {

    companion object {
        lateinit var instance: OilApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initMMKV()
        initTheme()
        initKoin()
    }

    private fun initMMKV() {
        MMKV.initialize(this).also {
            System.out.println("mmkv root: " + it)
        }
    }

    private fun initTheme() {
        if (currentThemeMode == null) currentThemeMode = MMKV.defaultMMKV().decodeParcelable("mmkv_theme", Theme::class.java)
    }

    private fun initKoin() {
        startKoin {
            AndroidLogger(level = Level.DEBUG)
            androidContext(this@OilApplication)
            modules(appModule)
        }
    }

}