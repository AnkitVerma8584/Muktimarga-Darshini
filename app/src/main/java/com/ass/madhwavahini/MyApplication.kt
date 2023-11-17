package com.ass.madhwavahini

import android.app.Application
import android.provider.Settings
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        lateinit var DEVICE_ID: String
    }

    override fun onCreate() {
        super.onCreate()
        DEVICE_ID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

}