// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.packageinstaller

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import org.luckypray.dexkit.query.enums.*

object InstallRiskDisable : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridgeList("InstallRiskDisable") {
            it.findMethod {
                matcher {
                    addUsingString("secure_verify_enable", StringMatchType.Equals)
                    returnType = "boolean"
                }
                matcher {
                    addUsingString("installerOpenSafetyModel", StringMatchType.Equals)
                    returnType = "boolean"
                }
                matcher {
                    addUsingString("android.provider.MiuiSettings\$Ad", StringMatchType.Equals)
                    returnType = "boolean"
                }
            }.toElementList()
        }.toMethodList().createHooks {
            returnConstant(false)
        }
    }
}
