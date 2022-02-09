package com.aire.android.aspectJ;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author ZhuPeipei
 * @date 2022/1/28 16:25
 */
@Aspect
public class WebViewAspectJ {
    private static final String TAG = "webviewAspectJ";

    @Pointcut("call(public android.webkit.WebView.new(..))")
    public void onWebViewCreate() {
        Log.i(TAG, "created");
    }

    @After("onWebViewCreate()")
    public void afterWebViewCreate(JoinPoint joinPoint) {
        Log.i(TAG, "afterWebViewCreate: " + joinPoint.getTarget());
    }
}
