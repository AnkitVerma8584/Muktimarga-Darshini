package com.ass.muktimargadarshini

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.util.locale.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun attachBaseContext(newBase: Context?) {
        appContext = newBase!!
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
}