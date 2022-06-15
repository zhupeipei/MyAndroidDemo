@file:JvmMultifileClass
@file:JvmName("ResourceExt")

package com.aire.android.util.size

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.aire.android.main.MainApplication

/**
 * Created by zhangkaikai on 2020/10/16.
 * @author zhangkaikai
 * @email kaikai.zhang@ximalaya.com
 * @phoneNumber 15721173906
 */

val Float.dp: Int
    inline get() = BaseUtil.dp2px(MainApplication.INSTANCE, this)

val Float.sp: Int
    inline get() = BaseUtil.sp2px(MainApplication.INSTANCE, this)

val Int.dp: Int
    inline get() = BaseUtil.dp2px(MainApplication.INSTANCE, this.toFloat())

val Int.sp: Int
    inline get() = BaseUtil.sp2px(MainApplication.INSTANCE, this.toFloat())

val Float.dpFloat: Float
    inline get() = BaseUtil.dp2pxReturnFloat(MainApplication.INSTANCE, this)

val Float.spFloat: Float
    inline get() = BaseUtil.sp2pxReturnFloat(MainApplication.INSTANCE, this)

val Int.dpFloat: Float
    inline get() = BaseUtil.dp2pxReturnFloat(MainApplication.INSTANCE, this.toFloat())

val Int.spFloat: Float
    inline get() = BaseUtil.sp2pxReturnFloat(MainApplication.INSTANCE, this.toFloat())

// 根据资源id 获取drawable
val Int.drawable: Drawable?
    inline get() = ContextCompat.getDrawable(MainApplication.INSTANCE, this)

// 根据资源id 获取颜色
val Int.color: Int
    inline get() = ContextCompat.getColor(MainApplication.INSTANCE, this)

val String.color: Int
    inline get() = Color.parseColor(this)