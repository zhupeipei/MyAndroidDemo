package com.aire.android.util.text

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.*
import android.view.View
import com.aire.android.util.CommUtils

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:07
 */
class SpanStringUtil {
}

/**
 * 将一段文字中指定range的文字改变大小
 * @param range 要改变大小的文字的范围
 * @param scale 缩放值，大于1，则比其他文字大；小于1，则比其他文字小；默认是1.5
 */
fun CharSequence.toSizeSpan(range: IntRange, scale: Float = 1.5f): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            RelativeSizeSpan(scale),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 将一段文字中指定range的文字改变前景色
 * @param range 要改变前景色的文字的范围
 * @param color 要改变的颜色，默认是红色
 */
fun CharSequence.toColorSpan(range: IntRange, color: Int = Color.RED): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            ForegroundColorSpan(color),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 将一段文字中指定range的文字改变背景色
 * @param range 要改变背景色的文字的范围
 * @param color 要改变的颜色，默认是红色
 */
fun CharSequence.toBackgroundColorSpan(range: IntRange, color: Int = Color.RED): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            BackgroundColorSpan(color),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 将一段文字中指定range的文字添加删除线
 * @param range 要添加删除线的文字的范围
 */
fun CharSequence.toStrikeThrougthSpan(range: IntRange): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            StrikethroughSpan(),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 将一段文字中指定range的文字添加颜色和点击事件
 * @param range 目标文字的范围
 */
fun CharSequence.toClickSpan(
    range: IntRange,
    color: Int = Color.RED,
    isUnderlineText: Boolean = false,
    clickAction: (() -> Unit)?
): CharSequence {
    return SpannableString(this).apply {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                clickAction?.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = color
                ds.isUnderlineText = isUnderlineText
            }
        }
        setSpan(clickableSpan, range.start, range.endInclusive, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 将一段文字中指定range的文字添加style效果
 * @param range 要添加删除线的文字的范围
 */
fun CharSequence.toStyleSpan(style: Int = Typeface.BOLD, range: IntRange): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            StyleSpan(style),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 将一段文字中指定range的文字添加自定义效果
 * @param range 要添加删除线的文字的范围
 */
fun CharSequence.toCustomTypeFaceSpan(typeface: Typeface, range: IntRange): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            CustomTypefaceSpan(typeface),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}


/**
 * 将一段文字中指定range的文字添加自定义效果,可以设置对齐方式，可以设置margin
 * @param range
 */
fun CharSequence.toImageSpan(
    imageRes: Int,
    range: IntRange,
    verticalAlignment: Int = 0,  //默认底部  4是垂直居中
    maginLeft: Int = 0,
    marginRight: Int = 0,
    width: Int = 0,
    height: Int = 0
): CharSequence {
    return SpannableString(this).apply {
        setSpan(
            MiddleIMarginImageSpan(
                CommUtils.getDrawable(imageRes)
                    .apply {
                        setBounds(
                            0,
                            0,
                            if (width == 0) getIntrinsicWidth() else width,
                            if (height == 0) getIntrinsicHeight() else height
                        )
                    },
                verticalAlignment,
                maginLeft,
                marginRight
            ),
            range.start,
            range.endInclusive,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}