// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.widget

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.setObjectField
import de.robv.android.xposed.XC_MethodHook

object AlwaysShowMiuiWidget : BaseHook() {
    override fun init() {
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null
        try {
            loadClass("com.miui.home.launcher.widget.WidgetsVerticalAdapter").methodFinder()
                .filterByName("buildAppWidgetsItems")
                .single()
        } catch (e: Exception) {
            loadClass("com.miui.home.launcher.widget.BaseWidgetsVerticalAdapter").methodFinder()
                .filterByName("buildAppWidgetsItems")
                .single()
        }.createHook {
            before {
                hook1 = loadClass("com.miui.home.launcher.widget.MIUIAppWidgetInfo").methodFinder()
                    .filterByName("initMiuiAttribute")
                    .filterByParamCount(1)
                    .single().createHook {
                        after {
                            it.thisObject.setObjectField("isMIUIWidget", false)
                        }
                    }
                hook2 = loadClass("com.miui.home.launcher.MIUIWidgetUtil").methodFinder()
                    .filterByName("isMIUIWidgetSupport")
                    .single().createHook {
                        after {
                            it.result = false
                        }
                }
            }
            after {
                hook1?.unhook()
                hook2?.unhook()
            }
        }
    }
}
