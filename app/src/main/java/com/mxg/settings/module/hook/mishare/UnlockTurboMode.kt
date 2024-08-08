// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mishare

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object UnlockTurboMode : BaseHook() {
    private val turboModeMethod by lazy {
        DexKit.getDexKitBridge("UnlockTurboMode") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("DeviceUtil", "xiaomi.hardware.p2p_160m")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    override fun init() {
        turboModeMethod.createHook {
            returnConstant(true)
        }
    }
}
