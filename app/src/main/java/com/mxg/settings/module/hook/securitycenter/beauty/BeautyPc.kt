// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.beauty

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod


object BeautyPc : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridge("BeautyPc") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("persist.vendor.camera.facetracker.support")
                    returnType = "boolean"
                }
            }.single().getMethodInstance(lpparam.classLoader)
        }.toMethod().createHook {
            returnConstant(true)
        }
    }
}
