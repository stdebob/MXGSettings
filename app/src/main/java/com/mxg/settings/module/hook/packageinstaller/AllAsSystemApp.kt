// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.packageinstaller

import android.content.pm.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList

object AllAsSystemApp : BaseHook() {
    private val systemMethod by lazy {
        DexKit.getDexKitBridgeList("AllAsSystemApp") {
            it.findMethod {
                matcher {
                    paramTypes = listOf("android.content.pm.ApplicationInfo")
                    returnType = "boolean"
                }
            }.toElementList()
        }.toMethodList()
    }

    override fun init() {
        systemMethod.createHooks {
            before { param ->
                (param.args[0] as ApplicationInfo).flags =
                    (param.args[0] as ApplicationInfo).flags.or(ApplicationInfo.FLAG_SYSTEM)
            }
        }
    }
}
