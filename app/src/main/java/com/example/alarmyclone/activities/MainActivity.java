package com.example.alarmyclone.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alarmyclone.R;
import com.example.alarmyclone.helpers.AlarmScheduler;
import com.example.alarmyclone.utils.TimeUtil;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button startCountdownButton;
    private TextView countdownText;

    private CountDownTimer countDownTimer;

    AlarmScheduler alarmScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alarmScheduler  = AlarmScheduler.getInstance(this);

        countdownText = findViewById(R.id.countdownText);
        startCountdownButton = findViewById(R.id.startCountdownButton);
        startCountdownButton.setOnClickListener(view -> startAlarm(10));
    }

    private void startAlarm(int secondsFromNow){
        alarmScheduler.setAlarm(secondsFromNow,1);
        startCountdown((long) secondsFromNow * 1000);
    }

    private void startCountdown(long totalMillis) {

        // Cancel any existing timer
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Start a new countdown timer
        countDownTimer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remainingHours = (int) (millisUntilFinished / 3600000);
                int remainingMinutes = (int) (millisUntilFinished % 3600000) / 60000;
                int remainingSeconds = (int) (millisUntilFinished % 60000) / 1000;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, remainingHours);
                calendar.set(Calendar.MINUTE, remainingMinutes);
                calendar.set(Calendar.SECOND, remainingSeconds);

                countdownText.setText(TimeUtil.formatCalendarTime(calendar, TimeUtil.DateFormatPattern.TIME_ONLY,null));
            }

            @Override
            public void onFinish() {
                // Countdown finished
                countdownText.setText("00:00:00");
            }
        }.start();
    }
}