// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.game

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object UnlockGunService : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridge("UnlockGunService") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addEqString("gb_game_collimator_status")
                    }
                    returnType = "boolean"
                    paramTypes = listOf("java.lang.String")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod().createHook {
            returnConstant(true)
        }
    }
}
