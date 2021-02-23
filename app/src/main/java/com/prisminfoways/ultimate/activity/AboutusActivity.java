package com.prisminfoways.ultimate.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityAboutusBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

public class AboutusActivity extends AppCompatActivity implements View.OnTouchListener, Handler.Callback {

    private static final String TAG = "AboutusActivity";
    ActivityAboutusBinding binding;
    private String aboutUsUrl;
    private String customerSupportUrl;
    CustomLoader customLoader;
    StoreUserData storeUserData;
    String headertext;
    private final Handler handler = new Handler(this);
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);

        storeUserData = new StoreUserData(this);

        aboutUsUrl = storeUserData.getString(Constants.ABOUT_US);
        customerSupportUrl = storeUserData.getString(Constants.CUSTOMER_SUPPORT);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        headertext = getIntent().getStringExtra("load_webview");

        if (headertext.equals("about_us")) {
            binding.backTxt.setText("About us");
        } else if (headertext.equals("custom_support")) {
            binding.backTxt.setText("Customer support");
        }

        startWebView();
    }
    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView() {

        WebView.setWebContentsDebuggingEnabled(true);

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
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d(TAG, "onLoadResource: " + url);
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (Uri.parse(url).getHost().equals("http://pbgzone.com/")) {

                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

            }
        });

        binding.webView.addJavascriptInterface(new Object() {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl) {
                Log.d(TAG,"perform");
            }
        }, "ok");

        if (getIntent().getStringExtra("load_webview").equals("about_us")) {
            binding.webView.loadUrl(aboutUsUrl);

        } else if (getIntent().getStringExtra("load_webview").equals("custom_support")) {
            binding.webView.loadUrl(customerSupportUrl);

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.webView && event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL) {
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW) {
            Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
