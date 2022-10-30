package com.example.alarmtask;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();
        sendNotification(k1);

        // Vibrate on receive
        Vibrator vibrator = (Vibrator) k1.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }

        // Get default ringtone
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // Setting default ringtone and play
        Ringtone ringtone = RingtoneManager.getRingtone(k1, alarmUri);
        ringtone.play();
    }

    public void sendNotification(Context context) {

        String channelId = "NotificationChannel";
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Alarm Task");
        builder.setContentText("Alarm Notification Received!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, "NotificationChannelName", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }

}