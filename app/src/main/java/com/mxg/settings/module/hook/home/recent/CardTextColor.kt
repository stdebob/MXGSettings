// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.widget.TextView
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.getObjectField
import com.mxg.settings.utils.hookAfterMethod

object CardTextColor : BaseHook() {
    override fun init() {
        val recentTextColor = mPrefsMap.getInt("home_recent_text_color", -1)
        if (recentTextColor != -1) {
            val taskViewHeaderClass = "com.miui.home.recents.views.TaskViewHeader".findClass()
            taskViewHeaderClass.hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleView") as TextView
                mTitle.setTextColor(recentTextColor)
            }
        }
    }
}
