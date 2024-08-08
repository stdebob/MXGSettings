// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.view.View
import android.widget.LinearLayout
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isMoreAndroidVersion
import com.mxg.settings.utils.devicesdk.isMoreHyperOSVersion

object RemoveSmartScreen : BaseHook() {
    override fun init() {
        if (isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34)) {
            loadClassOrNull("com.android.keyguard.injector.KeyguardBottomAreaInjector")!!.methodFinder()
                .filterByName("updateIcons")
                .single().createHook {
                    after {
                        val left =
                            ObjectUtils.getObjectOrNullAs<LinearLayout>(it.thisObject, "mLeftAffordanceViewLayout") ?: return@after
                        left.visibility = View.GONE
                    }
                }
        } else {
            loadClassOrNull("com.android.keyguard.negative.MiuiKeyguardMoveLeftViewContainer")!!.methodFinder()
                .filterByName("inflateLeftView")
                .single().createHook {
                    returnConstant(null)
                }
        }
    }

}
