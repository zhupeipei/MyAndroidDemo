package com.aire.android.webview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author ZhuPeipei
 * @date 2022/1/19 17:39
 */
public class WebViewUtil {
    private static final String TAG = "zimotag123";

    public static void createWebView(Context context, Handler handler) {
        final ReferenceQueue<WebView> queue = new ReferenceQueue<WebView>();
        final WeakReference<WebView> reference = new WeakReference<>(new WebView(context.getApplicationContext()), queue);
        System.gc();
        Runtime.getRuntime().gc();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Reference<? extends WebView> w = null;
                        try {
                            w = queue.remove();
                            if (w != null) {
                                Log.i(TAG, "run: " + w + ", " + w.get() + ", " + reference);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, 10_000);
    }
}
