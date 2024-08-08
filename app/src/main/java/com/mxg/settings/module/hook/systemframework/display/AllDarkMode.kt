// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.display

import android.content.pm.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.api.LazyClass.clazzMiuiBuild
import com.mxg.settings.utils.devicesdk.*
import de.robv.android.xposed.*

// from SetoHook by SetoSkins
class AllDarkMode : BaseHook() {
    override fun init() {
        if (isInternational()) return
        val clazzForceDarkAppListManager =
            loadClass("com.android.server.ForceDarkAppListManager")
        clazzForceDarkAppListManager.methodFinder().filterByName("getDarkModeAppList").toList()
            .createHooks {
                before {
                    val originalValue = XposedHelpers.getStaticBooleanField(clazzMiuiBuild, "IS_INTERNATIONAL_BUILD")
                    setStaticObject(clazzMiuiBuild, "IS_INTERNATIONAL_BUILD", true)
                    it.setObjectExtra("originalValue", originalValue)
                }
                after {
                    val originalValue = it.getObjectExtra("originalValue")
                    setStaticObject(clazzMiuiBuild, "IS_INTERNATIONAL_BUILD", originalValue)
                }
            }
        clazzForceDarkAppListManager.methodFinder().filterByName("shouldShowInSettings").toList()
            .createHooks {
                before { param ->
                    val info = param.args[0] as ApplicationInfo?
                    param.result =
                        !(info == null || (invokeMethodBestMatch(
                            info,
                            "isSystemApp"
                        ) as Boolean) || info.uid < 10000)
                }
            }
    }
}
