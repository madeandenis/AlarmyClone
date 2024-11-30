package com.example.alarmyclone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.alarmyclone.R;
import com.example.alarmyclone.helpers.AlarmScheduler;
import com.example.alarmyclone.services.AlarmService;
import com.example.alarmyclone.utils.TimeUtil;

import java.util.Calendar;

public class WakeUpActivity extends AppCompatActivity {

    private AlarmScheduler alarmScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_actions);

        TextView timeTextView = findViewById(R.id.timeTextView);
        AppCompatButton dismissButton = findViewById(R.id.dismissButton);
        AppCompatButton snoozeButton = findViewById(R.id.snoozeButton);

         alarmScheduler = AlarmScheduler.getInstance(null);

        timeTextView.setText(TimeUtil.formatCalendarTime(Calendar.getInstance(), TimeUtil.DateFormatPattern.HOUR_MINUTE_FORMAT,null));

        // Dismiss button logic
        dismissButton.setOnClickListener(v -> {
            dismissAlarm();
            finish();
        });

        snoozeButton.setOnClickListener(v -> {
            dismissAlarm();
            alarmScheduler.setAlarm(10,1);
            finish();
        });
    }

    private void dismissAlarm() {
        // Stop the alarm service
        Intent alarmServiceIntent = new Intent(this, AlarmService.class);
        stopService(alarmServiceIntent);
    }
}
