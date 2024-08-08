// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.battery

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object ScreenUsedTime : BaseHook() {
    private val method1 by lazy {
        DexKit.getDexKitBridge("ScreenUsedTime1") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("ishtar", "nuwa", "fuxi")
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }
    private val method2 by lazy {
        DexKit.getDexKitBridgeList("ScreenUsedTime2") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingStringsEquals("not support screenPowerSplit", "PowerRankHelperHolder")
                    }
                    returnType = "boolean"
                    paramCount = 0
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        Log.i("methods2 :$method2")
        method2.forEach {
            it.createHook {
                returnConstant(
                    when (it) {
                        method1 -> true
                        else -> false
                    }
                )
            }
        }
    }
}
