package com.iostyle.compoil.application

import android.app.Application
import com.tencent.mmkv.MMKV

class OilApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this).also {
            System.out.println("mmkv root: " + it)
        }

    }
}