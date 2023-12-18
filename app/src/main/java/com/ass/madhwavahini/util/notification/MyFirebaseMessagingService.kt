package com.ass.madhwavahini.util.notification

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.Keep
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.ui.presentation.authentication.AuthenticationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import java.net.URL
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
            if (userDataStore.getId() != 0) {
                val data: Map<String, String> = p0.data
                val title = data["title"] ?: "Title"
                val body = data["message"] ?: "Message"
                val image = data["image"]
                val mIcon = getImage(image)
                sendNotification(title, body, mIcon)
            }
        }
    }

    private fun sendNotification(title: String, body: String, image: Bitmap?) {
        val notificationIntent = Intent(applicationContext, AuthenticationActivity::class.java)

        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val id = (Int.MIN_VALUE..Int.MAX_VALUE).random()

        val intent = PendingIntent.getActivity(
            applicationContext,
            id,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        notificationHelper.sendNotification(id, title, body, intent, image)
    }

    private fun getImage(image: String?): Bitmap? {
        if (image == null)
            return null
        return try {
            val url = URL(image)
            BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            null
        }
    }

}