// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.voicetrigger;

import android.annotation.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import java.lang.reflect.*

object BypassUDKWordLegalCheck : BaseHook() {
    override fun init() {
        try {
            // Pangaea引擎录入时的联网检查
            DexKit.getDexKitBridge("BypassPangaeaWordCheck") {
                it.findMethod {
                    matcher {
                        addUsingStringsEquals("PangaeaTrainingSession", "onlineQuery=")
                        returnType = "java.lang.Boolean"
                    }
                }.single().getMethodInstance(lpparam.classLoader)
            }.toMethod().createHook {
                returnConstant(true)
            }
        } catch (_: Throwable) {
        }
        // 默认引擎录入时的联网检查
        try {
            DexKit.getDexKitBridge("BypassLegacyTrainingCheck") {
                it.findMethod {
                    matcher {
                        addUsingStringsEquals("LegacyTrainingSession", "onlineQuery=")
                        returnType = "java.lang.Boolean"
                    }
                }.single().getMethodInstance(lpparam.classLoader)
            }.toMethod().createHook {
                returnConstant(true)
            }
        } catch (_: Throwable) {
        }
        // 判断唤醒词是否合规
        try {
            DexKit.getDexKitBridge("BypassDefineWordCheck") {
                it.findMethod {
                    matcher {
                        addUsingStringsEquals(
                            "https://i.ai.mi.com/api/skillstore/assistant/store/visitors/checkWakeUpWord",
                            "NetUtils",
                            "checkUDKWordLegal, callResult="
                        )
                        returnType = "boolean"
                    }
                }.single().getMethodInstance(lpparam.classLoader)
            }.toMethod().createHook {
                returnConstant(true)
            }
        } catch (_: Throwable) {
        }
        // 根据对应的唤醒词得到其精度，并返回其是否可用
        val accUser = mPrefsMap.getInt("voicetrigger_accuracy_percent", 70).toFloat() / 100
        try {
            DexKit.getDexKitBridge("BypassOnlineAccuracyResult") {
                it.findMethod {
                    matcher {
                        addUsingStringsEquals(
                            "https://i.ai.mi.com/api/skillstore/assistant/store/visitors/checkWakeUpWord",
                            "NetUtils",
                            "getUDKEnrollWordLegal can't get result"
                        )
                        returnType = "java.lang.String"
                    }
                }.single().getMethodInstance(lpparam.classLoader)
            }.toMethod().createHook {
                returnConstant(
                    "{\"data\":{\"status\":0,\"msg\":\"\",\"accuracy\":\"" + String.format(
                        "%.1f",
                        accUser
                    ) + "\"}}"
                )
            }
        } catch (_: Throwable) {
        }
        try {
            // 禁止判断当前系统网络状态
            DexKit.getDexKitBridge("BypassNetworkStateCheckForUdkEnroll") {
                it.findMethod {
                    matcher {
                        addUsingStringsEquals("connectivity")
                        paramCount = 1
                        paramTypes("android.content.Context")
                        modifiers = Modifier.STATIC
                        returnType = "boolean"
                        opNames = listOf(
                            "const-string",
                            "invoke-virtual",
                            "move-result-object",
                            "check-cast",
                            "const/4",
                            "if-nez",
                            "return",
                            "invoke-virtual"
                        )

                    }
                }.single().getMethodInstance(lpparam.classLoader)
            }.toMethod().createHook {
                returnConstant(true)
            }
        } catch (_: Throwable) {
        }
    }
}
