// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.beauty

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import de.robv.android.xposed.*

object BeautyLightAuto : BaseHook() {
    private val beauty by lazy {
        DexKit.getDexKitBridge("superWirelessCharge") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("taoyao")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(EzXHelper.classLoader)
        }.toMethod()
    }
    private val beautyAuto by lazy {
        DexKit.getDexKitBridgeList("superWirelessCharge") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("taoyao")
                    returnType = "boolean"
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        if (mPrefsMap.getBoolean("security_center_beauty_face")) {
            beauty.createHook {
                returnConstant(true)
            }
        }

        beautyAuto.forEach {
            if (!java.lang.String.valueOf(it).contains("<clinit>")) {
                if (!java.lang.String.valueOf(it.name).contains(beauty.toString()) && it.name != beauty.name) {
                    logI(TAG, this.lpparam.packageName, "beautyLightAuto method is $beautyAuto")
                    XposedBridge.hookMethod(it, XC_MethodReplacement.returnConstant(true))
                }
            }
        }
    }
}
