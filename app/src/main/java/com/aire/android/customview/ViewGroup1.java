package com.aire.android.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aire.android.util.MotionEventUtil;

/**
 * @author ZhuPeipei
 * @date 2021/11/17 21:01
 */
public class ViewGroup1 extends FrameLayout {
    private static final String TAG = ViewGroup1.class.getSimpleName();

    public ViewGroup1(@NonNull Context context) {
        super(context);
    }

    public ViewGroup1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("viewIntercept", TAG + " dispatchTouchEvent: " + MotionEventUtil.getMotionEventStr(ev));
        boolean dispatch = super.dispatchTouchEvent(ev);
        Log.i("viewIntercept", "ViewGroup1 dispatchTouchEvent: " + Log.getStackTraceString(new Throwable()));
        return dispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("viewIntercept", TAG + " onInterceptTouchEvent: " + MotionEventUtil.getMotionEventStr(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("viewIntercept", TAG + " onTouchEvent: " + MotionEventUtil.getMotionEventStr(event));
        return super.onTouchEvent(event);
    }
}
