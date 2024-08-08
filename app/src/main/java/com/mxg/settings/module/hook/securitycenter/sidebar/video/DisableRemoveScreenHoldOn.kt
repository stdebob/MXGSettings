// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.video

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object DisableRemoveScreenHoldOn : BaseHook() {
    private val screen by lazy {
        DexKit.getDexKitBridge("DisableRemoveScreenHoldOn") {
            it.findMethod {
                matcher {
                    addEqString("remove_screen_off_hold_on")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    override fun init() {
        screen.createHook {
            returnConstant(false)
        }
    }
}
