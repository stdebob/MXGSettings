// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mediaeditor

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.api.LazyClass.AndroidBuildCls
import de.robv.android.xposed.*
import java.lang.reflect.*

object UnlockLeicaFilter : BaseHook() {
    private val leicaOld by lazy {
        DexKit.getDexKitBridgeList("UnlockLeicaFilterOld") { dexkit ->
            dexkit.findMethod {
                matcher {
                    // 仅适配 1.5 及 1.6 的部分版本，新版已更换检测方式
                    declaredClass {
                        addUsingStringsEquals("unSupportDeviceList", "stringResUrl")
                    }
                    modifiers = Modifier.FINAL
                    returnType = "boolean"
                    paramCount = 0
                }
            }.toElementList()
        }.toMethodList()
    }
    private val leicaNew by lazy {
        DexKit.getDexKitBridge("UnlockLeicaFilterNew") { dexkit ->
            dexkit.findMethod {
                matcher {
                    declaredClass = "com.miui.mediaeditor.photo.filter.repository.FilterRepository"
                    returnType = "java.io.Serializable"
                }
            }.single().getMethodInstance(classLoader)
        }.toMethod()
    }

    override fun init() {
        if (leicaOld.isNotEmpty()) {
            leicaOld.forEach { method ->
                logD(TAG, lpparam.packageName, "Old Leica name is $method") // debug 用
                method.createHook {
                    returnConstant(true)
                }
            }
        } else {
            logD(TAG, lpparam.packageName, "New Leica name is $leicaNew") // debug 用
            leicaNew.createHook {
                before {
                    XposedHelpers.setStaticObjectField(
                        AndroidBuildCls,
                        "DEVICE",
                        "aurora"
                    )
                }
            }
        }
    }
}
