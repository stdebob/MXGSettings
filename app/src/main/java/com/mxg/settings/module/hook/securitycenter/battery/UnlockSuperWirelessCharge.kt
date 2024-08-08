// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.battery

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object UnlockSuperWirelessCharge : BaseHook() {

    private val superWirelessCharge by lazy {
        DexKit.getDexKitBridge("superWirelessCharge") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("persist.vendor.tx.speed.control")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(EzXHelper.classLoader)
        }.toMethod()
    }

    private val superWirelessChargeTip by lazy {
        DexKit.getDexKitBridge("superWirelessChargeTip") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("key_is_connected_super_wls_tx")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(EzXHelper.classLoader)
        }.toMethod()
    }

    override fun init() {
        superWirelessCharge.createHook {
            returnConstant(true)
        }

        superWirelessChargeTip.createHook {
            returnConstant(true)
        }
    }
}
