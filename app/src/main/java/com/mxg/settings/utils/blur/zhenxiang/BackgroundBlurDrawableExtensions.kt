// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.blur.zhenxiang

import com.android.internal.graphics.drawable.*
import org.lsposed.hiddenapibypass.*

fun BackgroundBlurDrawable.setColor(color: Int) {
    HiddenApiBypass.invoke(BackgroundBlurDrawable::class.java, this, "setColor", color)
}

fun BackgroundBlurDrawable.setBlurRadius(blurRadius: Int) {
    HiddenApiBypass.invoke(BackgroundBlurDrawable::class.java, this, "setBlurRadius", blurRadius)
}

fun BackgroundBlurDrawable.setCornerRadius(
    cornerRadiusTL: Float,
    cornerRadiusTR: Float,
    cornerRadiusBL: Float,
    cornerRadiusBR: Float
) {
    HiddenApiBypass.invoke(
        BackgroundBlurDrawable::class.java,
        this,
        "setCornerRadius",
        cornerRadiusTL,
        cornerRadiusTR,
        cornerRadiusBL,
        cornerRadiusBR
    )
}
