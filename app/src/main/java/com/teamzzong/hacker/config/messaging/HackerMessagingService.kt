package com.teamzzong.hacker.config.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teamzzong.hacker.R
import com.teamzzong.hacker.data.model.request.toDeviceToken
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.notification.NotificationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HackerMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var service: UserService

    @Inject
    lateinit var dataStore: HackerDataStore

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (dataStore.userToken != "") {
            GlobalScope.launch {
                runCatching {
                    service.registerFirebaseToken(
                        dataStore.userToken, token.toDeviceToken()
                    )
                }.onFailure(Timber::e)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            for ((key, value) in message.data) {
                Timber.d("Nunu message key $key value $value")
            }
            sendNotificationAlarm(
                Message(message.data["title"].toString(), message.data["content"].toString())
            )
        } else {
            message.notification?.let {
                Timber.d("Nunu message key ${it.title} value $${it.body}")
                sendNotificationAlarm(Message(it.title.toString(), it.body.toString()))
            }
        }
    }

    private fun sendNotificationAlarm(message: Message) {
        val notifyId = (Clock.System.now().epochSeconds / 7).toInt()
        val intent = NotificationActivity.getIntent(this).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                notifyId,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

        val channelId = getString(R.string.default_notification_channel_id)

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(message.title).setContentText(message.body)
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH).setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService<NotificationManager>()
        val channel = NotificationChannel(
            channelId, channelId, NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager?.run {
            createNotificationChannel(channel)
            notify(notifyId, notificationBuilder.build())
        }
    }

    private data class Message(val title: String, val body: String)
}
