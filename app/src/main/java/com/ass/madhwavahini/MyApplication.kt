package com.ass.madhwavahini

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ass.madhwavahini.data.local.UserDataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltAndroidApp
class MyApplication : Application()