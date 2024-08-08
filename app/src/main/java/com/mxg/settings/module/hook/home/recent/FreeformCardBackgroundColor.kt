// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.hookAfterAllConstructors
import com.mxg.settings.utils.setIntField

object FreeformCardBackgroundColor : BaseHook() {
    override fun init() {
        val appCardBgColor = mPrefsMap.getInt("home_recent_freeform_background_color", -1)
        if (appCardBgColor != -1) {
            "com.miui.home.recents.views.TaskViewThumbnail".findClass().hookAfterAllConstructors {
                it.thisObject.setIntField("mBgColorForSmallWindow", appCardBgColor)
            }
        }
    }
}
