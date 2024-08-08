// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.view.View
import android.widget.TextView
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.getObjectField
import com.mxg.settings.utils.hookAfterMethod

object HideFreeform : BaseHook() {
    override fun init() {

        val recentsContainerClass = "com.miui.home.recents.views.RecentsContainer".findClass()
        if (mPrefsMap.getBoolean("home_recent_hide_freeform")) {
            recentsContainerClass.hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTxtSmallWindow") as TextView
                mTitle.visibility = View.GONE
            }
        }
    }
}
