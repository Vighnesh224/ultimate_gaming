package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.SettingPojo;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    StoreUserData storeUserData;
    private int currentVersion = 5;
    private String unity = "unity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        storeUserData = new StoreUserData(this);

        UnityAds.setListener(adsListener);
        UnityAds.initialize(SplashActivity.this, "3161787", adsListener, false);

        getBalance();
        getSettings();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (storeUserData.getBoolean(Constants.IS_LOGIN)) {
                    if (storeUserData.getInt(Constants.UPDATE_APP_VERSION) > currentVersion/*storeUserData.getInt(Constants.CURRENT_VERSION_CODE)*/) {
                        startActivity(new Intent(SplashActivity.this, UpdateActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 1000);
    }

    private void getBalance() {

        new AddShow().handleCall(SplashActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setString(Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
                        storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    private void getSettings() {


        new AddShow().handleCall(SplashActivity.this, add.Native.com.admodule.Constants.TAG_SETTING, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Log.d(TAG, "onLoaded: " + o.toString());

                SettingPojo settingPojo = new Gson().fromJson(o.toString(), SettingPojo.class);

                if (settingPojo.getStatus().equals("1")) {
                    storeUserData.setString(Constants.TELEGRAM_LINK, settingPojo.getData().getTelegram());
                    storeUserData.setString(Constants.ABOUT_US, settingPojo.getData().getAbout_us());
                    storeUserData.setString(Constants.CUSTOMER_SUPPORT, settingPojo.getData().getCustomer_support());
                    storeUserData.setString(Constants.TERMS_CONDITION, settingPojo.getData().getTerms_and_conditions());
                    storeUserData.setString(Constants.PRIVACY_POLICY, settingPojo.getData().getPrivacy_policy());
                    storeUserData.setString(Constants.FAQ, settingPojo.getData().getFaq());

                    storeUserData.setString(Constants.WEBSITE, settingPojo.getData().getWebsite());
                    storeUserData.setString(Constants.HOW_IT_WORK, settingPojo.getData().getHow_it_work());
                    storeUserData.setString(Constants.DATE, settingPojo.getData().getDate());
                    storeUserData.setString(Constants.INFO_MSG, settingPojo.getData().getInfo_msg());
                    storeUserData.setString(Constants.HOW_TO_JOIN_ROOM, settingPojo.getData().getHow_to_join_room());


                    storeUserData.setInt(Constants.UPDATE_APP_VERSION, settingPojo.getData().getUpdate().getVersion());
                    storeUserData.setString(Constants.UPDATE_MESSAGE, settingPojo.getData().getUpdate().getMessage());
                    storeUserData.setString(Constants.SKIP, settingPojo.getData().getUpdate().getSkip());
                    storeUserData.setString(Constants.APP_LINK, settingPojo.getData().getUpdate().getLink());
                } else {
                    Log.d("0", "0");
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }
    private IUnityAdsListener adsListener = new IUnityAdsListener() {

        @Override
        public void onUnityAdsReady(final String zoneId) {
            DeviceLog.debug("onUnityAdsReady: " + zoneId);
            Utilities.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (zoneId) {
                        case "banner_ads":
                        case "video":
                        case "defaultZone":
                        case "defaultVideoAndPictureZone":
                        case "fullscreen":
                            if (!UnityAds.isReady()) {
                                Log.d("1", "1");
                            }
                            break;

                        case "rewardedVideo":
                        case "rewardedVideoZone":
                        case "incentivizedZone":
                            break;
                        default: break;
                    }
                }
            });
        }

        @Override
        public void onUnityAdsStart(String zoneId) {
            DeviceLog.debug("onUnityAdsStart: " + zoneId);
            Log.d("Dishnat ", "start " + zoneId);

        }

        @Override
        public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {
            Log.d("Dishnat ", "finish " + zoneId + "  result  " + result);
            DeviceLog.debug("onUnityAdsFinish: " + zoneId + " - " + result);

            UnityAds.setListener(new IUnityAdsExtendedListener() {
                @Override
                public void onUnityAdsClick(String s) {
                    Log.d("", "clicked");
                }

                @Override
                public void onUnityAdsPlacementStateChanged(String s, UnityAds.PlacementState placementState, UnityAds.PlacementState placementState1) {
                    Log.d(unity, "statechanged");
                }

                @Override
                public void onUnityAdsReady(String s) {
                    Log.d(unity, "adsready");
                }

                @Override
                public void onUnityAdsStart(String s) {
                    Log.d(unity, "adsstart");
                }

                @Override
                public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                    Log.d(unity, "adsfinish");
                }

                @Override
                public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                    Log.d(unity, "adserror");
                }
            });

        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            Log.d("Dishant", error + "  Message  " + message);
            DeviceLog.debug("onUnityAdsError: " + error + " - " + message);
        }
    };

}
