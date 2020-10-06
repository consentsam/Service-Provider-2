package com.laundry.user.firebasenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.laundry.user.R


class MyFirebaseNotification : FirebaseMessagingService() {
    private val TAG = "MyFCMService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {

            Log.e(TAG, "Data payload: driver " + remoteMessage.data)

            val notificationType = remoteMessage.data["notification_type"] ?: ""
           // val senderId = remoteMessage.data["sender_id"] ?: ""
           // val parcelId = JSONObject(remoteMessage.data["parcel_data"]!!).getString("parcel_id")

           // Log.e(TAG, "Data driver $notificationType   $senderId  $parcelId")

            showNotification(notificationType)
        }
    }

    private fun showNotification(notificationType: String) {

        var pendingIntent: PendingIntent? = null

       /* when (notificationType) {
            "1" -> {
                val intent = Intent(this, DrawPathOnMapActivity::class.java)
                intent.putExtra("parcel_id", parcelId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                pendingIntent =
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            }
        }*/

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mBuilder = NotificationCompat.Builder(applicationContext, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Order status")
            .setColor(0x008000)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentText("New package delivery request")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(defaultSoundUri)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "my_notification"
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }
}