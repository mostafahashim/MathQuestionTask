package math.question.task.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import math.question.task.R
import math.question.task.view.activity.main.MainActivity


fun makeStatusNotification(isForeground: Boolean, message: String, context: Context): Notification {

    var intent: Intent? = null
    intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    intent.action = System.currentTimeMillis().toString()
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = context.getString(R.string.default_notification_channel_name)
        val description = message
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            context.getString(R.string.default_notification_channel_id),
            name,
            importance
        )
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    val pendingIntent = PendingIntent.getActivity(
        context, 0,
        intent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    // Create the notification
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.default_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("answering a Question")
        .setContentText(message)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)

    // Show the notification
    NotificationManagerCompat.from(context)
        .notify(if (isForeground) 4999 else 5000, builder.build())
    return builder.build()
}