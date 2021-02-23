package com.prisminfoways.ultimate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityHTMLGameDetailsBinding;
import com.wang.avi.CustomLoader;

public class HTML_GameDetailsActivity extends AppCompatActivity {

    private ActivityHTMLGameDetailsBinding binding;
    private Activity activity;
    private String gameID, playstore_url;
    private long TIME = 60 * 2 * 1000;
    private CountDownTimer mCountDownTimer;
    private InterstitialAd mInterstitialAd;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_t_m_l__game_details);
        activity = this;
        customLoader = new CustomLoader(activity, false);

        gameID = getIntent().getStringExtra("gameID");
        playstore_url = getIntent().getStringExtra("playstore_url");
        String gameName = getIntent().getStringExtra("gameName");

        binding.txtmultigame.setText(gameName);
        binding.shimmerLayout.startShimmerAnimation();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadWebView();

        startTimer();


    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(TIME, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                Log.v("Log_tag", "Tick of Progress" + millisUntilFinished);

            }

            @Override
            public void onFinish() {

                fullScreenAds();
            }
        };
        mCountDownTimer.start();


    }

    private void fullScreenAds() {
        customLoader.showLoader();
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(activity.getResources().getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {

            public void onAdLoaded() {
                if (customLoader != null)
                    customLoader.dismissLoader();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                }
            }

            @Override
            public void onAdClosed() {
                if (customLoader != null)
                    customLoader.dismissLoader();
                startTimer();


            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (customLoader != null)
                    customLoader.dismissLoader();
                startTimer();


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

    }

    private void loadWebView() {
        binding.htmlWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.shimmerLayout.stopShimmerAnimation();
                binding.shimmerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("Error", description);
            }

        });

        // All Requires For HTML Games.
        binding.htmlWebView.getSettings().setJavaScriptEnabled(true);
        binding.htmlWebView.getSettings().setAllowFileAccess(true);
        binding.htmlWebView.getSettings().setAllowContentAccess(true);
        binding.htmlWebView.getSettings().setDomStorageEnabled(true);
        binding.htmlWebView.getSettings().setLoadWithOverviewMode(true);
        binding.htmlWebView.getSettings().setUseWideViewPort(true);
        binding.htmlWebView.getSettings().setSupportMultipleWindows(true);
        binding.htmlWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.htmlWebView.isHorizontalScrollBarEnabled();
        binding.htmlWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.htmlWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.htmlWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        binding.htmlWebView.loadUrl(playstore_url);
    }
}