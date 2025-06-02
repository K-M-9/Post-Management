package com.myapplication.post.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.myapplication.post.R

object NotificationUtil {

    private const val CHANNEL_ID = "welcome_channel"
    private const val NOTIFICATION_ID = 1001


    fun isNotificationPermissionRequired(context: Context): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
    }

    fun notifyWelcome(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val title = context.getString(R.string.notification_welcome_title)
        val text = context.getString(R.string.notification_welcome_text)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }


    fun AppCompatActivity.checkAndNotify(requestLauncher: ActivityResultLauncher<String>) {
        if (isNotificationPermissionRequired(this)) {
            requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            notifyWelcome(this)
        }
    }
}