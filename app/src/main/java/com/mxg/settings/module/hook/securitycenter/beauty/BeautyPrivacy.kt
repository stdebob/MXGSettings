// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.beauty

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object BeautyPrivacy : BaseHook() {
    private val R0 by lazy {
        DexKit.getDexKitBridge("BeautyPrivacy") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("persist.sys.privacy_camera")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    private val invokeMethod by lazy {
        DexKit.getDexKitBridgeList("BeautyPrivacyList") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingStringsEquals("persist.sys.privacy_camera")
                    }
                    paramTypes = emptyList()
                    returnType = "boolean"
                    addInvoke {
                        declaredClass {
                            addUsingStringsEquals("persist.sys.privacy_camera")
                        }
                        returnType = R0.returnType.name
                        paramTypes = listOf(R0.parameterTypes[0].name)
                    }
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        R0.createHook {
            before {
                it.args[0] = true
            }
        }

        invokeMethod.createHooks {
            returnConstant(true)
        }
    }
}
