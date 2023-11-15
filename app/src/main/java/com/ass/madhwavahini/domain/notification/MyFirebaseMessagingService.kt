package com.ass.madhwavahini.domain.notification

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.Keep
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.CHANNEL_ONE_ID
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.ui.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Keep
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    @Inject
    lateinit var userDataStore: UserDataStore

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        CoroutineScope(Default).launch {
            if (userDataStore.shouldGetNotifications()) {
                val data: Map<String, String> = p0.data
                val title = data["title"] ?: "Title"
                val body = data["message"] ?: "Message"
                sendNotification(title, body)
            }
        }
    }

    private fun sendNotification(title: String, body: String) {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)

        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        val id = (1..50000).random()

        val intent = PendingIntent.getActivity(
            applicationContext,
            id,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationHelper.createNormalChannel()
            val notificationBuilder: Notification =
                notificationHelper.getNormalNotification(title, body, intent)

            notificationHelper.notify(id, notificationBuilder)
        } else {
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(applicationContext, CHANNEL_ONE_ID)
                    .setSmallIcon(R.drawable.app_logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setShowWhen(true)
                    .setContentIntent(intent)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            NotificationManagerCompat.from(applicationContext).notify(id, builder.build())

        }
    }

}