// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.view.*
import android.widget.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*

object RemoveIcon : BaseHook() {
    override fun init() {
        "com.miui.home.recents.views.TaskViewHeader".hookAfterMethod("onFinishInflate") {
            val mImage = it.thisObject.getObjectField("mIconView") as ImageView
            mImage.visibility = View.GONE
        }
    }
}
