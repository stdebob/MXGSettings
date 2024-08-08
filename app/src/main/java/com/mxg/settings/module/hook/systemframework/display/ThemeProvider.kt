// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.display

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import de.robv.android.xposed.XC_MethodHook
import miui.drm.DrmManager
import miui.drm.ThemeReceiver

class ThemeProvider : BaseHook() {
    override fun init() {
        var hook: List<XC_MethodHook.Unhook>? = null
        try {
            ThemeReceiver::class.java.methodFinder().filterByName("validateTheme").first().createHook {
                before {
                    hook = DrmManager::class.java.methodFinder().filterByName("isLegal").toList().createHooks {
                        returnConstant(DrmManager.DrmResult.DRM_SUCCESS)
                    }
                }
                after {
                    hook?.forEach { it.unhook() }
                }
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
    }
}
