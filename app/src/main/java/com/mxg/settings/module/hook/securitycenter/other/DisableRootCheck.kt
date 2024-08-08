// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.other

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

object DisableRootCheck : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridge("DisableRootCheck") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("key_check_item_root")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(lpparam.classLoader)
        }.toMethod().createHook {
            returnConstant(false)
        }
    }
}
