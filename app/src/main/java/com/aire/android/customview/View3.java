package com.aire.android.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.aire.android.util.MotionEventUtil;

/**
 * @author ZhuPeipei
 * @date 2021/11/17 21:09
 */
public class View3 extends View {
    private static final String TAG = View3.class.getSimpleName();

    public View3(Context context) {
        super(context);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("viewIntercept", TAG + " dispatchTouchEvent: " + MotionEventUtil.getMotionEventStr(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("viewIntercept", TAG + " onTouchEvent: " + MotionEventUtil.getMotionEventStr(event));
        return true;
//        return super.onTouchEvent(event);
    }
}
