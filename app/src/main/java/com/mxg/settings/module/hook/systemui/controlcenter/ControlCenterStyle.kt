// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.devicesdk.*

object ControlCenterStyle : BaseHook() {
    override fun init() {
        loadClass("com.android.systemui.controlcenter.policy.ControlCenterControllerImpl").declaredConstructors.createHooks {
            after {
                setObject(it.thisObject, "forceUseControlCenterPanel", false)
            }
        }

        if (isMoreAndroidVersion(34)) {
            loadClass("com.miui.interfaces.SettingsObserver")
        } else {
            loadClass("com.miui.systemui.SettingsObserver")
        }.methodFinder()
            .filterByName("setValue\$default").first()
            .createHook {
                before {
                    if (it.args[1] == "force_use_control_panel") {
                        it.args[2] = 0
                    }
                }
            }
    }
}
