// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mishare

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toField
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.*
import de.robv.android.xposed.*
import java.lang.reflect.*

object NoAutoTurnOff : BaseHook() {
    private val nullMethod by lazy {
        DexKit.getDexKitBridge("NoAutoTurnOff1") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("MiShareService", "EnabledState")
                    usingNumbers(600000L)
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val null2Method by lazy {
        DexKit.getDexKitBridge("NoAutoTurnOff2") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingStringsEquals("mishare:advertise_lock")
                    }
                    paramCount = 2
                    modifiers = Modifier.STATIC
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val null3Method by lazy {
        DexKit.getDexKitBridge("NoAutoTurnOff3") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("com.miui.mishare.action.GRANT_NFC_TOUCH_PERMISSION")
                    usingNumbers(600000L)
                    modifiers = Modifier.PRIVATE
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val toastMethod by lazy {
        DexKit.getDexKitBridgeList("NoAutoTurnOff4") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingStringsEquals("null context", "cta_agree")
                    }
                    returnType = "boolean"
                    paramTypes = listOf("android.content.Context", "java.lang.String")
                    paramCount = 2
                }
            }.toElementList()
        }.toMethodList()
    }

    private val nullField by lazy {
        DexKit.getDexKitBridge("NoAutoTurnOff3") {
            it.findField {
                matcher {
                    addReadMethod {
                        addUsingStringsEquals("NfcShareTaskManager")
                        returnType = "void"
                        paramCount = 1
                        modifiers = Modifier.PRIVATE
                    }
                    modifiers = Modifier.STATIC or Modifier.FINAL
                    type = "int"
                }
            }.singleOrNull()?.getFieldInstance(safeClassLoader)
        }.toField()
    }

    override fun init() {
        // 禁用小米互传功能自动关闭部分
        runCatching {
            setOf(nullMethod, null2Method).createHooks {
                returnConstant(null)
            }
        }

        runCatching {
            null3Method.createHook {
                after {
                    val d = it.thisObject.getObjectField("d")
                    XposedHelpers.callMethod(d, "removeCallbacks", it.thisObject)
                    logI(
                        TAG, this@NoAutoTurnOff.lpparam.packageName,
                        "null3Method hook success, $d"
                    )
                }
            }
        }

        runCatching {
            findAndHookConstructor(nullField!!.javaClass, object : MethodHook() {
                override fun after(param: MethodHookParam) {
                    XposedHelpers.setObjectField(param.thisObject, nullField!!.name, 999999999)
                    logI(TAG, lpparam.packageName, "nullField hook success, $nullField")
                } })
        }

        // 干掉小米互传十分钟倒计时 Toast
        toastMethod.createHooks {
            before { param ->
                if (param.args[1].equals("security_agree")) {
                    param.result = false
                }
            }
        }
    }
}
