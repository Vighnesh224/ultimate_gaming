package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityWebBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

public class WebActivity extends AppCompatActivity {

    ActivityWebBinding binding;

    StoreUserData storeUserData;
    String url;
    String type;
    CustomLoader customLoader;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_web);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        url = getIntent().getStringExtra("url");
        type = getIntent().getStringExtra("type");

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.txtType.setText(type);



        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setDisplayZoomControls(false);
        binding.webView.getSettings().setSupportMultipleWindows(false);

        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setDatabaseEnabled(true);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setSaveFormData(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.getSettings().setAllowFileAccess(true);
        binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.setClickable(true);
        binding.webView.setWebChromeClient(new WebChromeClient());

        binding.webView.setWebViewClient(new WebViewClient() {
            WebView view;
            String url;
            @Override
            public void onLoadResource(WebView view, String url) {
                this.view = view;
                this.url = url;
                super.onLoadResource(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
        });


        binding.webView.loadUrl(url);

    }


}