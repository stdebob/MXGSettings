// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toClass

object ShowAllHideApp : BaseHook() {
    override fun init() {
        val getClass = DexKit.getDexKitBridge("ShowAllHideApp") { bridge ->
            bridge.findClass {
                matcher {
                    addUsingStringsEquals("appInfo.packageName", "activityInfo")
                }
            }.single().getInstance(EzXHelper.classLoader)
        }.toClass()

        getClass.methodFinder()
            .filterByName("isHideAppValid")
            .single().createHook {
                returnConstant(true)
            }
    }
}
