// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import android.view.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*

object RemoveLeftShare : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.recents.views.RecentsWorldCirculateAndSmallWindowCrop").methodFinder()
            .filterByName("initViewDisplayInDrag")
            .first().createHook {
                before {
                    it.thisObject.setObjectField("mIsSupportWorldcirculate", false)
                }
                after {
                    val mWorldcirculateContent = it.thisObject.getObjectField("mWorldcirculateContent") as ViewGroup
                    mWorldcirculateContent.visibility = ViewGroup.GONE
                }
            }
    }
}
