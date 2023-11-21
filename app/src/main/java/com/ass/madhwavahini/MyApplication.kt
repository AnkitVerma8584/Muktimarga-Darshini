package com.ass.madhwavahini

import android.app.Application
import com.ass.madhwavahini.data.Constants
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.messaging.subscribeToTopic(Constants.TOPIC)
    }

}