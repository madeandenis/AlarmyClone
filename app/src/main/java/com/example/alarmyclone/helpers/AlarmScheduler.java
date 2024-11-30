package com.example.alarmyclone.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.alarmyclone.receivers.AlarmReceiver;
import com.example.alarmyclone.utils.TimeUtil;

import java.util.Calendar;

public class AlarmScheduler {

    private static AlarmScheduler instance;
    private Context context;
    private AlarmManager alarmManager;

    public AlarmScheduler(Context context) {
        this.context = context.getApplicationContext();
        initialize();
    }

    private void initialize() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static synchronized AlarmScheduler getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmScheduler(context);
        }
        return instance;
    }

    public void setAlarm(int secondsFromNow, int alarmId) {
        Calendar triggerTime = Calendar.getInstance();
        triggerTime.add(Calendar.SECOND, secondsFromNow);

        // Intent for the AlarmReceiver (replace with your receiver's class)
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        // Creating the PendingIntent with an immutable flag
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmId,  // Unique ID for the PendingIntent
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            // Setting a repeating alarm
            alarmManager.set(
                AlarmManager.RTC_WAKEUP, // Wake the device if it's asleep
                triggerTime.getTimeInMillis(), // The time when the alarm should trigger
                pendingIntent // The PendingIntent to fire when the alarm goes off
            );

            Toast.makeText(
                context,
                "Alarm set for: " + TimeUtil.formatCalendarTime(triggerTime, TimeUtil.DateFormatPattern.HOUR_MINUTE_FORMAT, null),
                Toast.LENGTH_SHORT
            ).show();
        }
    }
}
