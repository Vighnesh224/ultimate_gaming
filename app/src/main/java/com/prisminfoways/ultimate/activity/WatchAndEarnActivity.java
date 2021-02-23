package com.prisminfoways.ultimate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class WatchAndEarnActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG = "WatchAndEarnActivity";

    private long mLeftTimeInMillis;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 300000;
    private long mEndTime;
    private CountDownTimer mCountDownTimer;

    StoreUserData storeUserData;
    CustomLoader customLoader;
    Activity activity;

   private TextView shareCode;
   private TextView txtTimer;

    private RewardedVideoAd mRewardedVideoAd;
    private String taskCompleted = "TASK COMPLETED";
    private String watchVideo = "WATCH VIDEO";
    private String check = "check";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_and_earn);

        ImageView imgBack;
        activity = this;

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        imgBack = findViewById(R.id.imgBack);
        shareCode = findViewById(R.id.share_code);
        txtTimer = findViewById(R.id.txtTimer);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(storeUserData.getInt(Constants.WATCH_VIDEO_COUNT) == Constants.TOTAL_WATCH_VIDEO_COUNT){
            shareCode.setText(taskCompleted);
            shareCode.setEnabled(false);
        }else{
            shareCode.setText(watchVideo);
            shareCode.setEnabled(true);
        }

        shareCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(customLoader !=null){
                   customLoader.showLoader();
               }



                loadRewardedVideoAd();
            }
        });
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }
    private void startTime() {

        mEndTime = System.currentTimeMillis() + mLeftTimeInMillis;

        mCountDownTimer = new CountDownTimer(mLeftTimeInMillis, 1000) {
            @Override
            public void onTick(long l) {
                Log.d("OnTickCall: ", String.valueOf(l));
                mLeftTimeInMillis = l;
                updateCountDownText();
                shareCode.setEnabled(false);
                storeUserData.setBoolean(com.prisminfoways.ultimate.helper.Constants.NEW_TIMER_START, true);
                if (storeUserData.getBoolean(check)) {
                    storeUserData.setBoolean(check, false);
                }
            }

            @Override
            public void onFinish() {
                mLeftTimeInMillis = START_TIME_IN_MILLIS;
                mTimerRunning = false;
                storeUserData.setBoolean(com.prisminfoways.ultimate.helper.Constants.NEW_TIMER_START, false);

                shareCode.setText(watchVideo);
                shareCode.setEnabled(true);

            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) ((mLeftTimeInMillis / 1000) / 60);
        int second = (int) ((mLeftTimeInMillis / 1000) % 60);

        String timeFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
        if (storeUserData.getBoolean(com.prisminfoways.ultimate.helper.Constants.NEW_TIMER_START)) {
            txtTimer.setText(timeFormated);
            shareCode.setText("TRY AFTER  "+timeFormated);
        } else {
            shareCode.setText(watchVideo);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        mLeftTimeInMillis = preferences.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = preferences.getBoolean("timerRunning", false);

        Log.i("CallMethod", "OnStart Call");
        storeUserData.setBoolean(check, true);
        updateCountDownText();

        if (mTimerRunning) {
            mEndTime = preferences.getLong("endTime", 0);
            mLeftTimeInMillis = mEndTime - System.currentTimeMillis();

            if (mLeftTimeInMillis < 0) {
                mLeftTimeInMillis = 0;
                mTimerRunning = false;
                startTime();
            } else {
                startTime();
            }
        } else {
            shareCode.setText(watchVideo);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: Activity");
        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("millisLeft", mLeftTimeInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onRewarded(RewardItem reward) {

        callAddCoinApi();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d(TAG,"rewardedvideo");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d(TAG,"rewardedvideoadclose");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        if(customLoader !=null){
            customLoader.dismissLoader();
        }
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if(customLoader !=null){
            customLoader.dismissLoader();
        }
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d(TAG,"rewardedvideoadopen");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d(TAG,"rewardedvideostarted");
    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.d(TAG,"rewardedvideocompleted");
    }

    private void callAddCoinApi() {

        HashMap<String, String> map = new HashMap<>();
        map.put("title", storeUserData.getString(Constants.WATCH_VIDEO_TITLE));
        map.put("amount", storeUserData.getString(Constants.WATCH_VIDEO_COIN));

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_ADD_COIN, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        storeUserData.setInt(Constants.WATCH_VIDEO_COUNT,storeUserData.getInt(Constants.WATCH_VIDEO_COUNT)+1);

                        if(storeUserData.getInt(Constants.WATCH_VIDEO_COUNT) == Constants.TOTAL_WATCH_VIDEO_COUNT){
                            shareCode.setEnabled(false);
                            shareCode.setText(taskCompleted);
                        }else{
                            storeUserData.setBoolean(check, true);
                            shareCode.setEnabled(true);
                            startTime();
                        }

                    } else {
                        CustomLoader.showErrorDialog(activity, jsonObject.getString("msg"));
                        shareCode.setEnabled(false);
                        shareCode.setText(taskCompleted);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG,"failed");
            }
        }, false);

    }
}