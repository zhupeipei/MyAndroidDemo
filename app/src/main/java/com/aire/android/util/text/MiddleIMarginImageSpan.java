package com.aire.android.util.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:21
 * 支持设置图片左右间距的 ImageSpan
 * 继承与居中的imageSpan
 * 我一般用这个，功能更多  已经用于扩展Span中
 */
public class MiddleIMarginImageSpan extends AlignMiddleImageSpan {

    private int mSpanMarginLeft = 0;
    private int mSpanMarginRight = 0;
    private int mOffsetY = 0;

    public MiddleIMarginImageSpan(Drawable d, int verticalAlignment, int marginLeft, int marginRight) {
        this(d, verticalAlignment, marginLeft, marginRight, 0);
    }

    public MiddleIMarginImageSpan(Drawable d, int verticalAlignment, int marginLeft, int marginRight, int offsetY) {
        super(d, verticalAlignment);
        mSpanMarginLeft = marginLeft;
        mSpanMarginRight = marginRight;
        mOffsetY = offsetY;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (mSpanMarginLeft != 0 || mSpanMarginRight != 0) {
            super.getSize(paint, text, start, end, fm);
            Drawable d = getDrawable();
            return d.getIntrinsicWidth() + mSpanMarginLeft + mSpanMarginRight;
        } else {
            return super.getSize(paint, text, start, end, fm);
        }
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        canvas.translate(0, mOffsetY);
        // marginRight不用专门处理，只靠getSize()中改变即可
        super.draw(canvas, text, start, end, x + mSpanMarginLeft, top, y, bottom, paint);
        canvas.restore();
    }
}