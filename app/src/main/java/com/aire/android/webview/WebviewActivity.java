package com.aire.android.webview;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewRenderProcess;
import android.webkit.WebViewRenderProcessClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FrameLayout rootFl = findViewById(R.id.main_webview_container);
        rootFl.addView(newWebView(1));

        rootFl.addView(newWebView(2));

        TextView tv = findViewById(R.id.main_webview_tv);
    }

    private WebView newWebView(final int num) {
        WebView webview = new WebView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("zimotag222", "newWebView: set success");
            webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_WAIVED, true);
        }
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                if (view != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                    view.destroy();
                    view = null;
                }

                // 默认返回false
                Log.i("zimotag222", "onRenderProcessGone: " + num + Log.getStackTraceString(new Throwable()) + "\n" + Thread.currentThread().getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i("zimotag2223", "onRenderProcessGone: " + num + ", " + detail.didCrash() + ", " + detail.rendererPriorityAtExit() + ", " + detail.toString());
                }
                return true;
            }
        };
        webview.setWebViewClient(client);
        webview.setWebChromeClient(new WebChromeClient());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("chrome://crash");
//        webview.loadUrl("https://m.baidu.com");

        return webview;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            webview.setWebViewRenderProcessClient(new Executor() {
//                @Override
//                public void execute(Runnable command) {
//                    Log.d("zimotag222", "Executor execute");
//                }
//            }, new WebViewRenderProcessClient() {
//                @Override
//                public void onRenderProcessUnresponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
//                    Log.d("zimotag222", "onRenderProcessUnresponsive");
//                }
//
//                @Override
//                public void onRenderProcessResponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
//                    Log.d("zimotag222", "onRenderProcessResponsive");
//                }
//            });
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            webview.setWebViewRenderProcessClient(new WebViewRenderProcessClient() {
//                @Override
//                public void onRenderProcessUnresponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
//                    Log.i("zimotag", "onRenderProcessUnresponsive: ");
//                }
//
//                @Override
//                public void onRenderProcessResponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
//                    Log.i("zimotag", "onRenderProcessResponsive: ");
//                }
//            });
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Log.i("zimotag1", "onResume: " + webview.getRendererRequestedPriority());
//            webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
//        }
    }

    private void oldWebView() {
        WebView webview = null;//findViewById(R.id.main_webview_crash_test);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webview.setWebViewRenderProcessClient(new WebViewRenderProcessClient() {
                @Override
                public void onRenderProcessUnresponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
                    Log.i("zimotag", "onRenderProcessUnresponsive: ");
                }

                @Override
                public void onRenderProcessResponsive(@NonNull WebView view, @Nullable WebViewRenderProcess renderer) {
                    Log.i("zimotag", "onRenderProcessResponsive: ");
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("zimotag1", "onResume: " + webview.getRendererRequestedPriority());
            webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
        }
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                // 默认返回false
                Log.i("zimotag222", "onRenderProcessGone: " + Log.getStackTraceString(new Throwable()) + "\n" + Thread.currentThread().getId());

                return false;

//                if (webview != null) {
//                    ((ViewGroup)webview.getParent()).removeView(webview);
//                    webview.destroy();
//                }
//                return true;

//                // WebViewClient.onRenderProcessGone was added in O.
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                    return false;
//                }
//                super.onRenderProcessGone(view, detail);
//
//                if (!detail.didCrash()) { // 这里表示webview被系统杀掉
//                    // Renderer was killed because the system ran out of memory.
//                    // The app can recover gracefully by creating a new WebView instance
//                    // in the foreground.
//                    Log.e("MY_APP", "System killed the WebView rendering process " +
//                            "to reclaim memory. Recreating...");
//
//                    if (view != null) {
//                        ((ViewGroup)view.getParent()).removeView(view);
//                        view.destroy();
//                        view = null;
//                    }
//
//                    // By this point, the instance variable "view" is guaranteed
//                    // to be null, so it's safe to reinitialize it.
//
//                    return true; // The app continues executing.
//                }
//
//                // Renderer crashed because of an internal error, such as a memory
//                // access violation.
//                Log.e("MY_APP", "The WebView rendering process crashed!");
//
//                // In this example, the app itself crashes after detecting that the
//                // renderer crashed. If you choose to handle the crash more gracefully
//                // and allow your app to continue executing, you should 1) destroy the
//                // current WebView instance, 2) specify logic for how the app can
//                // continue executing, and 3) return "true" instead.
//                return false;
            }
        };
        webview.setWebViewClient(client);

        WebChromeClient webchromeClient = new WebChromeClient();
        webview.setWebChromeClient(webchromeClient);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("chrome://crash");
//        webview.loadUrl("https://m.baidu.com");
    }
}