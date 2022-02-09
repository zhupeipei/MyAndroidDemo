package com.aire.android.aspectJ

import android.os.Bundle
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.aire.android.test.R

class WebViewTestForAspectJInKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_test_for_aspect_jactivity)
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        findViewById<FrameLayout>(R.id.aspect_webview_container_fl).addView(newWebView(), lp)
    }

    private fun newWebView(): WebView {
        val webView = WebView(this)
        webView.loadUrl("https://www.baidu.com")
        return webView
    }
}