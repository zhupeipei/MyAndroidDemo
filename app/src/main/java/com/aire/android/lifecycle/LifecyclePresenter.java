package com.aire.android.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author ZhuPeipei
 * @date 2021/12/24 21:29
 */
public class LifecyclePresenter implements LifecycleObserver {
    private static final String TAG = "life";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        Log.i(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.i(TAG, "onResume: ");
    }
}
