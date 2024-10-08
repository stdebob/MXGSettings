// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethod
import com.mxg.settings.utils.callStaticMethod
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.getObjectField
import com.mxg.settings.utils.hookAfterMethod
import com.mxg.settings.utils.replaceMethod
import com.mxg.settings.utils.setObjectField

object RemoveCardAnim : BaseHook() {
    override fun init() {

        "com.miui.home.recents.views.SwipeHelperForRecents".hookAfterMethod("onTouchEvent", MotionEvent::class.java) {
            if (it.thisObject.getObjectField("mCurrView") != null) {
                val taskView2 = it.thisObject.getObjectField("mCurrView") as View
                taskView2.alpha = 1f
                taskView2.scaleX = 1f
                taskView2.scaleY = 1f
            }
        }
        "com.miui.home.recents.TaskStackViewLayoutStyleHorizontal".replaceMethod(
            "createScaleDismissAnimation", View::class.java, Float::class.java
        ) {
            val view = it.args[0] as View
            val getScreenHeight =
                findClass("com.miui.home.launcher.DeviceConfig").callStaticMethod("getScreenHeight") as Int
            val ofFloat =
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.translationY, -getScreenHeight * 1.1484375f)
            ofFloat.duration = 200
            return@replaceMethod ofFloat
        }

        "com.miui.home.recents.views.VerticalSwipe".hookAfterMethod("calculate", Float::class.java) {
            val f = it.args[0] as Float
            val asScreenHeightWhenDismiss =
                "com.miui.home.recents.views.VerticalSwipe".findClass()
                    .callStaticMethod("getAsScreenHeightWhenDismiss") as Int
            val f2 = f / asScreenHeightWhenDismiss
            val mTaskViewHeight = it.thisObject.getObjectField("mTaskViewHeight") as Float
            val mCurScale = it.thisObject.getObjectField("mCurScale") as Float
            val f3: Float = mTaskViewHeight * mCurScale
            val i = if (f2 > 0.0f) 1 else if (f2 == 0.0f) 0 else -1
            val afterFrictionValue: Float =
                it.thisObject.callMethod("afterFrictionValue", f, asScreenHeightWhenDismiss) as Float
            if (i < 0) it.thisObject.setObjectField(
                "mCurTransY",
                (mTaskViewHeight / 2.0f + afterFrictionValue * 2) - (f3 / 2.0f)
            )
        }

    }
}
