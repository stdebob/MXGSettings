// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.other

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList

object FuckRiskPkg : BaseHook() {
    private val pkg by lazy {
        DexKit.getDexKitBridgeList("FuckRiskPkg") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals(
                        "riskPkgList", "key_virus_pkg_list", "show_virus_notification"
                    )
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        pkg.createHooks {
            returnConstant(null)
        }
    }
}
