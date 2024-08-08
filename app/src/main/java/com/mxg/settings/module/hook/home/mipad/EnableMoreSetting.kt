// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.mipad

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectField

object EnableMoreSetting : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.settings.MiuiHomeSettings").methodFinder().first{
            name == "checkDevice"
        }.createHook{
            returnConstant(true)
        }

        loadClass("com.miui.home.launcher.DeviceConfig").methodFinder().first{
            name == "needShowCellsEntry"
        }.createHook{
            returnConstant(true)
        }

        loadClass("com.miui.home.launcher.LauncherMenu").methodFinder().first{
            name == "onShow"
        }.createHook{
            after{
                val mDefaultScreenPreview = it.thisObject.getObjectField("mDefaultScreenPreview") as View
                mDefaultScreenPreview.visibility = View.VISIBLE
            }
        }
    }
}
