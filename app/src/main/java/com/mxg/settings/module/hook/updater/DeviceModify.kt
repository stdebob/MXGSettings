// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater

import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.utils.*


object DeviceModify : BaseHook() {
    private val deviceName: String = mPrefsMap.getString("updater_device", "")
    override fun init() {
        try {
            "android.os.SystemProperties".hookBeforeMethod(
                "get", String::class.java, String::class.java
            ) {
                if (it.args[0] == "ro.product.mod_device") it.result = deviceName
            }
        } catch (e: Throwable) {
            logE(TAG, "[DeviceModify(Updater)]: android.os.SystemProperties hook failed", e)
        }
        try {
            "miuix.core.util.SystemProperties".hookBeforeMethod(
                "get", String::class.java, String::class.java
            ) {
                if (it.args[0] == "ro.product.mod_device") it.result = deviceName
            }
        } catch (e: Throwable) {
            logE(
                TAG,
                "[DeviceModify(Updater)]: DeviceModify (Updater) miuix.core.util.SystemProperties hook failed",
                e
            )
        }
        DexKit.getDexKitBridgeList("DeviceModify") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("android.os.SystemProperties", "get", "get e")
                }
            }.toElementList()
        }.toMethodList().forEach { method ->
            method.hookBeforeMethod {
                if (it.args[0] == "ro.product.mod_device") it.result = deviceName
            }
            logI(TAG, this.lpparam.packageName, "dexkit method is $method")
        }

        /*try {
            val systemProperties = mUpdaterResultMethodsMap["SystemProperties"]!!
            assert(systemProperties.size == 1)
            val systemPropertiesDescriptor = systemProperties.first()
            val systemPropertiesMethod: Method =
                systemPropertiesDescriptor.getMethodInstance(lpparam.classLoader)
            systemPropertiesMethod.hookBeforeMethod {
                if (it.args[0] == "ro.product.mod_device") it.result = deviceName
            }
            logI("(Updater) dexkit method is $systemPropertiesMethod")
        } catch (e: Throwable) {
            XposedBridge.log("[MxGSettings][E][DeviceModify(Updater)]: DeviceModify (Updater) dexkit hook failed by $e")
        }*/
    }
}
