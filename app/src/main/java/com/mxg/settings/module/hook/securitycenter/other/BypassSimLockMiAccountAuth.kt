// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.other

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import org.luckypray.dexkit.query.enums.*

object BypassSimLockMiAccountAuth : BaseHook() {
    private val findMethod by lazy {
        DexKit.getDexKitBridgeList("BypassSimLockMiAccountAuth") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingString("SimLockUtils", StringMatchType.Contains)
                    }
                    addCall {
                        addUsingString("SimLockStartFragment::simLockSetUpFlow::step =", StringMatchType.Contains)
                    }
                    paramCount = 1
                    paramTypes("android.content.Context")
                    returnType = "boolean"
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        logD(TAG, lpparam.packageName, "BypassSimLockMiAccountAuth find method is ${findMethod.last()}")
        findMethod.last().createHook {
            returnConstant(true)
        }
    }
}
