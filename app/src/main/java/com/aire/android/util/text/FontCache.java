package com.aire.android.util.text;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:32
 */
public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "SF-UI-Text/" + fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}
