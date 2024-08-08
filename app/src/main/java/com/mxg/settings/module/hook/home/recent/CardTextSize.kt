// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.getObjectField
import com.mxg.settings.utils.hookAfterMethod

object CardTextSize : BaseHook() {
    override fun init() {
        val recentTextSize = mPrefsMap.getInt("home_recent_text_size", -1)
        if (recentTextSize != -1) {
            val taskViewHeaderClass = "com.miui.home.recents.views.TaskViewHeader".findClass()
            taskViewHeaderClass.hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleView") as TextView
                mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, recentTextSize.toFloat())
                if (recentTextSize == 0) mTitle.visibility = View.GONE
            }
        }
    }
}
