// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object NoPassword : BaseHook() {
    override fun init() {
        loadClass("com.android.internal.widget.LockPatternUtils\$StrongAuthTracker").methodFinder()
            .filterByName("isBiometricAllowedForUser")
            .first().createHook {
                returnConstant(true)
            }

        loadClass("com.android.internal.widget.LockPatternUtils").methodFinder()
            .filterByName("isBiometricAllowedForUser")
            .first().createHook {
                returnConstant(true)
            }
    }

}
