package com.ass.madhwavahini.domain.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_DESCRIPTION
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_ID
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@TargetApi(Build.VERSION_CODES.O)
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var notificationManager: NotificationManager? = null

    fun createNormalChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ONE_ID,
            CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.description = CHANNEL_ONE_DESCRIPTION
        notificationChannel.setShowBadge(true)
        notificationChannel.enableVibration(true)
        notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager().createNotificationChannel(notificationChannel)
    }

    fun getNormalNotification(
        title: String?,
        body: String?,
        myIntent: PendingIntent?
    ): Notification {
        return Notification.Builder(context, CHANNEL_ONE_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(myIntent)
            .setStyle(Notification.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.app_logo)
            .setShowWhen(true)
            .setAutoCancel(true)
            .build()
    }

    fun notify(id: Int, notification: Notification) {
        getManager().notify(id, notification)
    }

    private fun getManager(): NotificationManager {
        if (notificationManager == null) {
            notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager as NotificationManager
    }
}