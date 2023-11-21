package com.ass.madhwavahini.util.notification

import android.Manifest
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_DESCRIPTION
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_ID
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_NAME
import com.ass.madhwavahini.util.print
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Creating channel".print()
            createChannel()
        }
    }

    private var notificationManager: NotificationManager? = null

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ONE_ID,
            CHANNEL_ONE_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.description = CHANNEL_ONE_DESCRIPTION
        notificationChannel.setShowBadge(true)
        notificationChannel.enableVibration(true)
        notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager().createNotificationChannel(notificationChannel)
    }


    internal fun sendNotification(
        id: Int,
        title: String?,
        body: String?,
        myIntent: PendingIntent? = null,
        mIcon: Bitmap? = null
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val b: Icon? = null
        val builder = NotificationCompat.Builder(context, CHANNEL_ONE_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(myIntent)
            .setSmallIcon(R.drawable.app_logo)
            .setShowWhen(true)
            .setAutoCancel(true)
            .setLargeIcon(mIcon)

        if (mIcon != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(mIcon).bigLargeIcon(b))
        } else {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(body))
        }


        notify(id, builder.build())
    }

    private fun notify(id: Int, notification: Notification) {
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