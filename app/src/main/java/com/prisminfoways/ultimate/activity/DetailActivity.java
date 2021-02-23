package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityDetailBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;
import com.unity3d.services.banners.view.BannerPosition;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    ActivityDetailBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    CountDownTimer countDownTimer;
    private String impressioncount;
    String urlLink;
    String onUnityBannerError = "onUnityBannerError";
    private View bannerView;
    private String bannerPlacementId = "bannerads";

    private CountDownTimer adlefttimer = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("Time **** : ", millisUntilFinished / 1000 + "");
        }

        @Override
        public void onFinish() {
            Toast.makeText(DetailActivity.this, "20 second was completed", Toast.LENGTH_SHORT).show();
            callAddCoinApi("Extra Bonus");
            callimpression();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        final IUnityBannerListener unityBannerListener = new UnityBannerListener();
        UnityBanners.setBannerListener(unityBannerListener);

        if (bannerView == null) {
            UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER);
            UnityBanners.loadBanner(this, bannerPlacementId);
        } else {
            UnityBanners.destroy();
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        urlLink = getIntent().getStringExtra("link");

        impressioncount = "impression_count" + com.prisminfoways.ultimate.helper.Constants.DATE;

        if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(com.prisminfoways.ultimate.helper.Constants.AD_WEB_CLICK) == 0) {

            binding.timer.setVisibility(View.GONE);

            new MaterialShowcaseView.Builder(this)
                    .setTarget(binding.introView)
                    .setContentText(storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.AD_CLICK_MSG))
                    .setDismissOnTouch(true)
                    .withRectangleShape()
                    .show();

        } else {
            if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(com.prisminfoways.ultimate.helper.Constants.AD_CLICK) == 0) {
                binding.timer.setVisibility(View.GONE);
            } else {
                binding.timer.setVisibility(View.VISIBLE);

                countDownTimer = new CountDownTimer(5000, 500) {
                    @Override
                    public void onTick(long l) {
                        int i = (int) (l / 500);
                        binding.timer.setText(String.valueOf(i / 2));
                    }

                    @Override
                    public void onFinish() {
                        binding.timer.setVisibility(View.GONE);
                        if (!storeUserData.getBoolean(Constants.IS_ACTION)) {
                            callAddCoinApi("News Bonus");
                            callimpression();
                        }
                    }
                }.start();
            }
        }



        startWebView(urlLink);

    }

    private void callimpression() {
        int impcount = storeUserData.getInt(impressioncount);
        impcount = impcount + 1;
        storeUserData.setInt(impressioncount, impcount);
        Log.d("imp_count", "callimpression: " + storeUserData.getInt(impressioncount));

    }

    @SuppressWarnings("deprecation")
    private void startWebView(String link) {

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setDisplayZoomControls(false);
        binding.webView.getSettings().setSupportMultipleWindows(false);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings()
                .setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; sdk Build/KRT16L) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("TestData", "onLoadResource : " + url);
            }

            //If you will not use this method url links are opeen in new brower not in webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://play.google.com/store/apps/details") || url.contains("app.goo.gl")) {
                    Log.d("AdClicked", "AdClicked");
                } else {
                    if (url.contains("http://netbywell.com/ci_news")) {
                        view.loadUrl(url);
                    } else {
                        try {

                            if (url.contains("https://googleads")) {
                                if (storeUserData.getInt(impressioncount) != 0 && storeUserData.getInt(impressioncount) % storeUserData.getInt(com.prisminfoways.ultimate.helper.Constants.AD_WEB_CLICK) == 0) {
                                    adlefttimer.start();
                                } else {
                                    Log.d(TAG,"left not start");
                                }
                            }
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setPackage("com.android.chrome");
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (Exception e) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                }
                return true;
            }
        });
        binding.webView.loadUrl(link);
    }

    private class UnityBannerListener implements IUnityBannerListener {

        @Override
        public void onUnityBannerLoaded(String placementId, View view) {
            bannerView = view;
            ((ViewGroup) findViewById(R.id.adView)).addView(view);
            Log.d(onUnityBannerError, "onUnityBannerLoaded: " + placementId);
        }

        @Override
        public void onUnityBannerUnloaded(String placementId) {
            bannerView = null;
            Log.d(onUnityBannerError, "onUnityBannerUnloaded: " + placementId);
        }

        @Override
        public void onUnityBannerShow(String placementId) {
            Log.d(onUnityBannerError, "onUnityBannerShow: " + placementId);
        }

        @Override
        public void onUnityBannerClick(String placementId) {
            Log.d(onUnityBannerError, "onUnityBannerClick: " + placementId);

        }

        @Override
        public void onUnityBannerHide(String placementId) {
            Log.d(onUnityBannerError, "onUnityBannerHide: " + placementId);
        }

        @Override
        public void onUnityBannerError(String message) {
            Log.d(onUnityBannerError, "onUnityBannerError: " + message);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adlefttimer != null) {
            adlefttimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void callAddCoinApi(String title) {

        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("amount", "0.5");

        new AddShow().handleCall(DetailActivity.this, Constants.TAG_ADD_COIN, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Log.d(TAG, "onLoaded: " + o.toString());
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        Log.d(TAG, "onLoaded: " + jsonObject.getString("msg"));
                    } else {
                        CustomLoader.showErrorDialog(DetailActivity.this, jsonObject.getString("msg"));
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
}
