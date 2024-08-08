// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isMoreAndroidVersion
import com.mxg.settings.utils.devicesdk.isMoreHyperOSVersion
import com.mxg.settings.utils.setObjectField

object HideLockscreenZenMode : BaseHook() {
    private val zenModeClass by lazy {
        loadClass("com.android.systemui.statusbar.notification.zen.ZenModeViewController")
    }

    override fun init() {
        // hyperOS fix by hyper helper
        if (isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34)) {
            zenModeClass.methodFinder()
                .filterByName("updateVisibility")
                .single().createHook {
                    before {
                        it.thisObject.setObjectField("manuallyDismissed", true)
                    }
                }
        } else {
            zenModeClass.methodFinder()
                .filterByName("shouldBeVisible")
                .single().createHook {
                    returnConstant(false)
                }
        }
    }
}
