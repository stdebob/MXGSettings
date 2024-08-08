// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mediaeditor

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import java.lang.reflect.*

object UnlockDisney : BaseHook() {
    private val mickey by lazy {
        DexKit.getDexKitBridge("UnlockDisneyMickey") {
            it.findMethod {
                matcher {
                    addCall {
                        addUsingStringsEquals("magic_recycler_matting_0", "magic_recycler_clear_icon")
                        // returnType = "java.util.List" // 你米 1.6.5.10.2 改成了 java.util.ArrayList，所以找不到
                        paramCount = 0
                    }
                    modifiers = Modifier.STATIC
                    returnType = "boolean"
                    paramCount = 0
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val bear by lazy {
        DexKit.getDexKitBridge("UnlockDisneyBear") {
            it.findMethod {
                matcher {
                    declaredClass = mickey.declaringClass.name
                    modifiers = Modifier.STATIC
                    returnType = "boolean"
                    paramCount = 0
                }
            }.last().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val isType by lazy {
        mPrefsMap.getStringAsInt("mediaeditor_unlock_disney_some_func", 0)
    }

    override fun init() {
        logD(TAG, lpparam.packageName, "disney Mickey name is $mickey")
        logD(TAG, lpparam.packageName, "disney Bear name is $bear")

        when (isType) {
            1 -> {
                isHook(mickey, true)
                isHook(bear, false)
            }
            2 -> {
                isHook(mickey, false)
                isHook(bear, true)
            }
        }
    }

    private fun isHook(method: Method, bool: Boolean) {
        method.createHook {
            returnConstant(bool)
        }
    }
}
