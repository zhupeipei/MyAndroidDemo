package com.aire.android.util.text.dsl

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toast
import com.aire.android.main.MainApplication
import com.aire.android.test.R
import com.aire.android.util.size.dp
import com.aire.android.util.text.*

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:27
 */
interface DslSpannableStringBuilder {
    //增加一段文字
    fun addText(text: String, method: (DslSpanBuilder.() -> Unit)? = null)

    //添加一个图标
    fun addImage(
        imageRes: Int,
        verticalAlignment: Int = 0,
        maginLeft: Int = 0,
        marginRight: Int = 0,
        width: Int = 0,
        height: Int = 0
    )
}

class DslSpannableStringBuilderImpl : DslSpannableStringBuilder {

    private val builder = SpannableStringBuilder()

    //添加文本
    override fun addText(text: String, method: (DslSpanBuilder.() -> Unit)?) {

        val spanBuilder = DslSpanBuilderImpl()
        method?.let { spanBuilder.it() }

        var charSeq: CharSequence = text

        spanBuilder.apply {
            if (issetColor) {
                charSeq = charSeq.toColorSpan(0..text.length, textColor)
            }
            if (issetBackground) {
                charSeq = charSeq.toBackgroundColorSpan(0..text.length, textBackgroundColor)
            }
            if (issetScale) {
                charSeq = charSeq.toSizeSpan(0..text.length, scaleSize)
            }
            if (isonClick) {
                charSeq = charSeq.toClickSpan(0..text.length, textColor, isuseUnderLine, onClick)
            }
            if (issetTypeface) {
                charSeq = charSeq.toCustomTypeFaceSpan(typefaces, 0..text.length)
            }
            if (issetStrikethrough) {
                charSeq = charSeq.toStrikeThrougthSpan(0..text.length)
            }

            builder.append(charSeq)
        }
    }

    //添加图标
    override fun addImage(
        imageRes: Int,
        verticalAlignment: Int,
        maginLeft: Int,
        marginRight: Int,
        width: Int,
        height: Int
    ) {
        var charSeq: CharSequence = "1"
        charSeq = charSeq.toImageSpan(
            imageRes,
            0..1,
            verticalAlignment,
            maginLeft,
            marginRight,
            width,
            height
        )
        builder.append(charSeq)
    }

    fun build(): SpannableStringBuilder {
        return builder
    }

}

interface DslSpanBuilder {
    //设置文字颜色
    fun setColor(color: Int = 0)

    //设置点击事件
    fun setClick(useUnderLine: Boolean = true, onClick: (() -> Unit)?)

    //设置缩放大小
    fun setScale(scale: Float = 1.0f)

    //设置自定义字体
    fun setTypeface(typeface: Typeface)

    //是否需要中划线
    fun setStrikethrough(isStrikethrough: Boolean = false)

    //设置背景
    fun setBackground(color: Int = Color.TRANSPARENT)

}


class DslSpanBuilderImpl : DslSpanBuilder {
    var issetColor = false
    var textColor: Int = Color.BLACK

    var isonClick = false
    var isuseUnderLine = false
    var onClick: (() -> Unit)? = null

    var issetScale = false
    var scaleSize = 1.0f

    var issetTypeface = false
    var typefaces: Typeface = Typeface.DEFAULT

    var issetStrikethrough = false

    var issetBackground = false
    var textBackgroundColor = 0

    override fun setColor(color: Int) {
        issetColor = true
        textColor = color
    }

    override fun setClick(useUnderLine: Boolean, onClick: (() -> Unit)?) {
        isonClick = true
        isuseUnderLine = useUnderLine
        this.onClick = onClick
    }

    override fun setScale(scale: Float) {
        issetScale = true
        scaleSize = scale
    }

    override fun setTypeface(typeface: Typeface) {
        issetTypeface = true
        typefaces = typeface
    }

    override fun setStrikethrough(isStrikethrough: Boolean) {
        issetStrikethrough = isStrikethrough
    }

    override fun setBackground(color: Int) {
        issetBackground = true
        textBackgroundColor = color
    }

}

//为 TextView 创建扩展函数，其参数为接口的扩展函数
fun TextView.buildSpannableString(init: DslSpannableStringBuilder.() -> Unit) {
    //具体实现类
    val spanStringBuilderImpl = DslSpannableStringBuilderImpl()
    spanStringBuilderImpl.init()
    movementMethod = LinkMovementMethod.getInstance()
    //通过实现类返回SpannableStringBuilder
    text = spanStringBuilderImpl.build()
}

fun demo() {
    TextView(MainApplication.INSTANCE).buildSpannableString {
        addText("我已详细阅读并同意")
        addText("测试红色的文字颜色") {
            setColor(Color.RED)
        }
        addText("测试白色文字加上灰色背景") {
            setColor(Color.WHITE)
            setBackground(Color.GRAY)
        }
        addText("测试文本变大了") {
            setColor(Color.DKGRAY)
            setScale(1.5f)
        }
        addImage(R.mipmap.ic_launcher)
        addText("测试可以点击的文本") {
            setClick(true) {
                Toast.makeText(MainApplication.INSTANCE, "点击文本拉啦啦", Toast.LENGTH_SHORT).show()
            }
        }
        addImage(R.mipmap.ic_launcher_round, 5, 10.dp, 10.dp, 35.dp, 35.dp)
        addText("Test Custom Typeface Font is't Success?") {
            setTypeface(TypefaceUtil.getSFFlower(MainApplication.INSTANCE))
        }
        addText("测试中划线是否生效") {
            setStrikethrough(true)
        }
    }
}