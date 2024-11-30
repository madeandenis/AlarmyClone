package com.example.alarmyclone.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.alarmyclone.R;
import com.example.alarmyclone.utils.NotificationUtil;

public class AlarmService extends Service {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public static final String ALARM_CHANNEL_ID = "alarm_service_channel";
    public static final String ALARM_CHANNEL_NAME = "Alarm Service";

    // Called when the service is created => starts the alarm sound and vibration
    @Override
    public void onCreate() {
        super.onCreate();
        startAlarmSound();
        triggerVibration();
    }

    private void startAlarmSound() {
        mediaPlayer = MediaPlayer.create(this,R.raw.miau_miau_miau);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void triggerVibration() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 1000}; // Vibrate for 1 second, pause for 1 second
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(pattern, 0); // Repeat indefinitely
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(pattern, 0); // Repeat indefinitely
        }
    }

    // Called when the service is started => starts as a foreground service with a notification
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, createNotification());
        return START_STICKY; // Ensures the service restarts if killed
    }

    // Called when the service is destroyed => stops the alarm sound and vibration
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Notification createNotification(){
        NotificationUtil.createNotificationChannel(
                this,
                ALARM_CHANNEL_ID,
                ALARM_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        return NotificationUtil.createNotification(
                this,
                ALARM_CHANNEL_ID,
                "Alarm Service",
                "Alarm is ringing",
                R.drawable.baseline_alarm_off_24
        );
    }


}
