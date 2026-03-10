package com.summer.threemforth;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class MyNotificationReceiver extends BroadcastReceiver {
    private Intent intent;

    public void onReceive(Context context, Intent intent2) {
        this.intent = intent2;
        createNotification(context, intent2.getStringExtra("name"), intent2.getStringExtra("plan"));
    }

    private void createNotification(Context context, String str, String str2) {
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(EditDialog$1$$ExternalSyntheticApiModelOutline0.m("channel_id", "channel_name", 3));
            builder = new NotificationCompat.Builder(context, "channel_id");
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setSmallIcon((int) R.drawable.baseline_notifications_24);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
