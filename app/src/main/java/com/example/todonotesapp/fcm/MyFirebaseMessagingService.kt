package com.example.todonotesapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.todonotesapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {
    val TAG = "MyMessagingService"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, message?.from.toString())
        Log.d(TAG, message?.data.toString())
        Log.d(TAG, message?.notification?.body.toString())

        setupNotification(message.notification?.body)

    }

    private fun setupNotification(body: String?) {
        val channelId = "Todo ID"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificatonBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round).setContentTitle("To do notes App")
            .setContentText(body).setSound(ringtone)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "To-do Notes", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificatonBuilder.build())

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}