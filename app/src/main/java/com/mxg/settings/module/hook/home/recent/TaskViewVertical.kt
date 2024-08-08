// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.graphics.RectF
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callStaticMethod
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.replaceMethod

object TaskViewVertical : BaseHook() {
    override fun init() {

        val value = mPrefsMap.getInt("home_recent_vertical_task_view_card_size", 100).toFloat() / 100
        if (value == -1f || value == 1f) return
        "com.miui.home.recents.views.TaskStackViewsAlgorithmVertical".replaceMethod(
            "scaleTaskView", RectF::class.java
        ) {
            "com.miui.home.recents.util.Utilities".findClass().callStaticMethod(
                "scaleRectAboutCenter",
                it.args[0],
                value * "com.miui.home.recents.util.Utilities".findClass()
                    .callStaticMethod("getTaskViewScale", appContext) as Float
            )
        }

    }
}
