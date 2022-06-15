package com.aire.android.util.text;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:04
 * String字符串通过区间来改变颜色，大小，字体，下划线等
 */
public class SpanUtils {

    private static final SpanUtils ourInstance = new SpanUtils();

    public static SpanUtils getInstance() {
        return ourInstance;
    }

    private SpanUtils() {
    }

    /**
     * 变大变小
     */
    public CharSequence toSizeSpan(CharSequence charSequence, int start, int end, float scale) {

        SpannableString spannableString = new SpannableString(charSequence);

        spannableString.setSpan(
                new RelativeSizeSpan(scale),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 变色
     */
    public CharSequence toColorSpan(CharSequence charSequence, int start, int end, int color) {

        SpannableString spannableString = new SpannableString(charSequence);

        spannableString.setSpan(
                new ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 变背景色
     */
    public CharSequence toBackgroundColorSpan(CharSequence charSequence, int start, int end, int color) {

        SpannableString spannableString = new SpannableString(charSequence);

        spannableString.setSpan(
                new BackgroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private long mLastClickTime = 0;
    public static final int TIME_INTERVAL = 1000;

    /**
     * 可点击-带下划线
     */
    public CharSequence toClickSpan(CharSequence charSequence, int start, int end, int color, boolean needUnderLine, OnSpanClickListener listener) {

        SpannableString spannableString = new SpannableString(charSequence);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (listener != null) {
                    //防止重复点击
                    if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
                        //to do
                        listener.onClick(charSequence.subSequence(start, end));

                        mLastClickTime = System.currentTimeMillis();
                    }

                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(color);
                ds.setUnderlineText(needUnderLine);
            }
        };

        spannableString.setSpan(
                clickableSpan,
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public interface OnSpanClickListener {
        void onClick(CharSequence charSequence);
    }


    /**
     * 变成自定义的字体
     */
    public CharSequence toCustomTypeFaceSpan(CharSequence charSequence, int start, int end, Typeface typeface) {

        SpannableString spannableString = new SpannableString(charSequence);

        spannableString.setSpan(
                new MyTypefaceSpan(typeface),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

}