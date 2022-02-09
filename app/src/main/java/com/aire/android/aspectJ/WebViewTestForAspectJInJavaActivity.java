package com.aire.android.aspectJ;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

public class WebViewTestForAspectJInJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test_for_aspect_jin_java);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout fl = findViewById(R.id.aspect_webview_container_fl);
        fl.addView(newWebView(), lp);
    }

    private WebView newWebView() {
        WebView webView = new WebView(this);
        webView.loadUrl("https://www.baidu.com");
        return webView;
    }
}