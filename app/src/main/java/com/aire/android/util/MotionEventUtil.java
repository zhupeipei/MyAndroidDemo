package com.aire.android.util;

import android.view.MotionEvent;

import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;

/**
 * @author ZhuPeipei
 * @date 2021/11/17 21:24
 */
public class MotionEventUtil {
    public static String getMotionEventStr(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "down";
            case MotionEvent.ACTION_MOVE:
                return "move";
            case MotionEvent.ACTION_UP:
                return "up";
            case MotionEvent.ACTION_CANCEL:
                return "cancel";
            default:
                return event.getAction() + "";
        }
    }
}
