// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.game

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

class GamePerformanceWildMode : BaseHook() {
    override fun init() {
        // 开放均衡/狂暴模式
        DexKit.getDexKitBridge("GamePerformanceWildMode") {
            it.findMethod {
                matcher {
                    addEqString("support_wild_boost")
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod().createHook {
            returnConstant(true)
        }
    }
}
