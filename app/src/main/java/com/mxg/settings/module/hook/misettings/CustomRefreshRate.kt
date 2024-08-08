// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.misettings

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.isFinal
import com.github.kyuubiran.ezxhelper.MemberExtensions.isStatic
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object CustomRefreshRate : BaseHook() {
    private val resultMethod by lazy {
        DexKit.getDexKitBridge("CustomRefreshRate") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("btn_preferce_category")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }
    override fun init() {
        val resultClass = loadClass("com.xiaomi.misettings.display.RefreshRate.RefreshRateActivity")

        resultMethod.createHook {
            before {
                it.args[0] = true
            }
        }

        resultClass.declaredFields.first { field ->
            field.isFinal && field.isStatic
        }.apply { isAccessible = true }.set(null, true)
    }
}
