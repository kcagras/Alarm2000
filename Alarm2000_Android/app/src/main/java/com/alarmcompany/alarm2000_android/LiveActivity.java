package com.alarmcompany.alarm2000_android;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Kawa on 24.03.2016.
 */
public class LiveActivity extends BaseActivity {

    WebView liveWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String frameVideo = "<html><body><iframe width=\"350\" height=\"206\" src=\"https://www.youtube.com/embed/sFukyIIM1XI\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        liveWebView = (WebView) findViewById(R.id.livecam_webview);
        liveWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = liveWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        liveWebView.loadData(frameVideo, "text/html", "utf-8");


    }
}
