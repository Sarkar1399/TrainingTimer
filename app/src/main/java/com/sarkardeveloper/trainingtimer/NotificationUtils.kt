package com.sarkardeveloper.trainingtimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {

    fun startNotification() {
        createNotificationChannel()

        val intent = Intent(TrainingTimerApp.appContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_ONE_SHOT

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(TrainingTimerApp.appContext, 0, intent, flag)

        val builder = NotificationCompat.Builder(TrainingTimerApp.appContext, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cart)
            .setContentTitle(TrainingTimerApp.getString(R.string.notification_title))
            .setContentText(TrainingTimerApp.getString(R.string.notification_content_text))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(TrainingTimerApp.getString(R.string.notification_content_text))
            ).setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)

        with(NotificationManagerCompat.from(TrainingTimerApp.appContext)) {
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = TrainingTimerApp.getString(R.string.app_name)
            val descriptionText = TrainingTimerApp.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                TrainingTimerApp.curActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}