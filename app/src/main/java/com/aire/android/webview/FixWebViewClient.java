package com.aire.android.webview;

import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author ZhuPeipei
 * @date 2022/2/14 10:53
 */
public class FixWebViewClient extends WebViewClient {
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        return super.onRenderProcessGone(view, detail);
    }
}
