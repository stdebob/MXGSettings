// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.blur.zhenxiang

import android.util.*
import android.view.*
import com.android.internal.graphics.drawable.*
import org.lsposed.hiddenapibypass.*

fun View.createBackgroundBlurDrawable(): BackgroundBlurDrawable? {

    return try {
        val getViewRootImpl =
            HiddenApiBypass.invoke(View::class.java, this, "getViewRootImpl") as ViewRootImpl
        HiddenApiBypass.invoke(
            ViewRootImpl::class.java,
            getViewRootImpl,
            "createBackgroundBlurDrawable"
        ) as BackgroundBlurDrawable
    } catch (e: Exception) {
        Log.w(null, e)
        null
    }
}
