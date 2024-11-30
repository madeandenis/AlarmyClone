package com.example.alarmyclone.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.alarmyclone.activities.WakeUpActivity;
import com.example.alarmyclone.services.AlarmService;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Wake up already!!!", Toast.LENGTH_LONG).show();

        Intent wakeUpIntent = new Intent(context, WakeUpActivity.class);
        // Necessary because activities cannot be started directly from a BroadcastReceiver
        wakeUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(wakeUpIntent);

        // Service is created
        Intent serviceIntent = new Intent(context, AlarmService.class);
        // Service is started
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }
}
