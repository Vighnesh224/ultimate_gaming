package com.prisminfoways.ultimate;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class MyGameCountDownTimer {

    private static final String TAG = "MyGameCountDownTimer";
    private String time;
    private String days;
    private String hours;
    private String minutes;
    private String seconds;
    CountDownTimer timer;
    private long startTimer;
    private TextView textView;

    public MyGameCountDownTimer(long startTimer, TextView textView) {
        this.startTimer = startTimer;
        this.textView = textView;
    }

    public void startCountDownTimer() {
        if (timer != null){
            timer.cancel();
        }

        timer = new CountDownTimer(startTimer, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: ");
                long diff = millisUntilFinished;

                long secondInMilli = 1000;
                long minuteInMilli = secondInMilli * 60;
                long hoursInMilli = minuteInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long mdays = diff / daysInMilli;
                if (mdays > 0){
                    days = String.valueOf(mdays);
                } else {
                    days = "0";
                }
                diff %= daysInMilli;

                long mHours = diff / hoursInMilli;
                if (mHours > 10){
                    hours = String.valueOf(mHours);
                } else {
                    hours = "0" + mHours;
                }
                diff %= hoursInMilli;

                long mMinutes = diff / minuteInMilli;
                if (mMinutes > 10){
                    minutes = String.valueOf(mMinutes);
                } else {
                    minutes = "0" + mMinutes;
                }
                diff %= minuteInMilli;

                long mSeconds = diff / secondInMilli;
                if (mSeconds > 10){
                    seconds = String.valueOf(mSeconds);
                } else {
                    seconds = "0" + mSeconds;
                }

                time = "- "+days+" days : "+ hours+" hours" + " : " + minutes +" min"+ " : " + seconds+" sec.";

                textView.setText(time);

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                textView.setText("Registration closed.");
            }
        };
        timer.start();
    }
}
