package com.prisminfoways.ultimate.fragment.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.DetailActivity;
import com.prisminfoways.ultimate.databinding.FragmentPoliticsBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

/**
 * A simple {@link Fragment} subclass.
 */

public class PoliticsFragment extends Fragment {

    private static final String TAG = "PoliticsFragment";
    FragmentPoliticsBinding binding;
    String url = "http://netbywell.com/ci_news/news_list.php?type=politics";
    CustomLoader customLoader;
    private StoreUserData storeUserData;
    Activity activity;
    boolean firsttimeloading;
    private String impressioncount;

    private CountDownTimer adlefttimer = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("Time **** : ", millisUntilFinished / 1000 + "");
        }

        @Override
        public void onFinish() {
            Toast.makeText(activity, "20 second was completed", Toast.LENGTH_SHORT).show();
            callAddCoinApi("Extra Bonus");
            callimpression();
            storeUserData.setBoolean(Constants.IS_ACTION, false);
        }
    };

    public PoliticsFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            loadWebView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_politics, container, false);
        activity = getActivity();

        impressioncount = "impression_count" + Constants.DATE;

        UnityAds.setListener(adsListener);
        UnityAds.initialize(activity, getString(R.string.game_id), adsListener, false);

        customLoader = new CustomLoader(activity, false);
        storeUserData = new StoreUserData(activity);


        loadWebView();

        return binding.getRoot();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebView() {

        customLoader.showLoader();

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDomStorageEnabled(false);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                customLoader.dismissLoader();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                storeUserData.setString("link", url);
                loadunity(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                customLoader.dismissLoader();
            }
        });

        binding.webView.loadUrl(url);
    }

    private void loadunity(String url) {
        customLoader.showLoader();

        if (!UnityAds.isInitialized()) {
            firsttimeloading = true;
            UnityAds.setListener(adsListener);
            UnityAds.initialize(activity, getString(R.string.game_id), adsListener, false);
        } else {
            if (UnityAds.isReady()) {
                firsttimeloading = false;
                UnityAds.show(activity);
            }

        }
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
                            if (UnityAds.isReady()) {
                                UnityAds.show(activity);

                            } else {
                                Intent intent = new Intent(activity, DetailActivity.class).putExtra("link", storeUserData.getString("link"));
                                startActivity(intent);
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
            customLoader.dismissLoader();
            DeviceLog.debug("onUnityAdsStart: " + zoneId);
            Log.d("Dishnat ", "start " + zoneId);
            if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(Constants.AD_CLICK) == 0) {
                Toast.makeText(activity, "" + storeUserData.getString(Constants.AD_CLICK_MSG), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {
            customLoader.dismissLoader();
            Log.d("Dishnat ", "finish " + zoneId + "  result  " + result);
            DeviceLog.debug("onUnityAdsFinish: " + zoneId + " - " + result);
            startActivity(new Intent(activity, DetailActivity.class).putExtra("link", storeUserData.getString("link")));

            UnityAds.setListener(new IUnityAdsExtendedListener() {
                @Override
                public void onUnityAdsClick(String s) {
                    if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(Constants.AD_CLICK) == 0) {
                        adlefttimer.start();
                    }
                }

                @Override
                public void onUnityAdsPlacementStateChanged(String s, UnityAds.PlacementState placementState, UnityAds.PlacementState placementState1) {
                    Log.d(TAG,"state changed");
                }

                @Override
                public void onUnityAdsReady(String s) {
                    Log.d(TAG,"ad ready");
                }

                @Override
                public void onUnityAdsStart(String s) {
                    customLoader.dismissLoader();
                    if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(Constants.AD_CLICK) == 0) {
                        Toast.makeText(activity, "" + storeUserData.getInt(Constants.AD_CLICK_MSG), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                    customLoader.dismissLoader();
                    startActivity(new Intent(activity, DetailActivity.class).putExtra("link", storeUserData.getString("link")));
                }

                @Override
                public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                    customLoader.dismissLoader();
                    startActivity(new Intent(activity, DetailActivity.class).putExtra("link", storeUserData.getString("link")));
                }
            });

        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            customLoader.dismissLoader();
            Log.d("Dishant", error + "  Message  " + message);
            DeviceLog.debug("onUnityAdsError: " + error + " - " + message);
            startActivity(new Intent(activity, DetailActivity.class).putExtra("link", storeUserData.getString("link")));
        }
    };

    private void callAddCoinApi(String title) {

        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("amount", "0.5");

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_ADD_COIN, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        Log.d(TAG, "onLoaded: " + jsonObject.getString("msg"));
                    } else {
                        CustomLoader.showErrorDialog(activity, jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: ");
            }
        }, false);
    }

    private void callimpression() {
        int impcount = storeUserData.getInt(impressioncount);
        impcount = impcount + 1;
        storeUserData.setInt(impressioncount, impcount);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adlefttimer != null) {
            adlefttimer.cancel();
        }
    }

}
