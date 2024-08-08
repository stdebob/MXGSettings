// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.video

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

class VBVideoMode : BaseHook() {
    override fun init() {
        // 开放影院/自定义模式
        DexKit.getDexKitBridge("VBVideoMode") {
            it.findMethod {
                matcher {
                    usingStrings = listOf("TheatreModeUtils")
                    usingNumbers = listOf(32)
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod().createHook {
            returnConstant(true)
        }
    }
}
