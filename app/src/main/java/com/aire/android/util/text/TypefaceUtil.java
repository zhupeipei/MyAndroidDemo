package com.aire.android.util.text;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:31
 */
public class TypefaceUtil {

    public static Typeface getSFLight(Context context) {
        return FontCache.getTypeface("SF-UI-Text-Light.otf", context);
    }

    public static Typeface getSFMedium(Context context) {
        return FontCache.getTypeface("San-Francisco-Display-Medium.ttf", context);
    }

    public static Typeface getSFRegular(Context context) {
        return FontCache.getTypeface("San-Francisco-Display-Regular.ttf", context);
    }

    public static Typeface getSFFlower(Context context) {
        return FontCache.getTypeface("IBMPlexSerif-Medium.ttf", context);
    }

    public static Typeface getSFSemobold(Context context) {
        return FontCache.getTypeface("SanFranciscoText-Semibold.otf", context);
    }
}