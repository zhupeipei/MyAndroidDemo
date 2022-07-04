package com.aire.android.doubleReflect;

import android.util.Log;

import androidx.annotation.Keep;

/**
 * @author ZhuPeipei
 * @date 2022/7/4 11:35
 */
@Keep
public class TestClazz {
    private static final String TAG = "testClazz";

    private static TestClazz INSTANCE = new TestClazz();

    public static TestClazz getInstance() {
        return INSTANCE;
    }

    private void say() {
        Log.i(TAG, "say: ");
        sayHello();
        Log.i(TAG, "say: ");
    }

    private void sayHello() {
        Log.i(TAG, "sayHello: " + Log.getStackTraceString(new Throwable()));
    }

    private void startService() {
        Log.i(TAG, "startService: " + Log.getStackTraceString(new Throwable()));
    }
}
