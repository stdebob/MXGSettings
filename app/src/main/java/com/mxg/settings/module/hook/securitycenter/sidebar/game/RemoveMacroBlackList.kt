// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.game

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toClass
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod

class RemoveMacroBlackList : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridge("RemoveMacroBlackList1") {
            it.findMethod {
                matcher {
                    addEqString("pref_gb_unsupport_macro_apps")
                    paramCount = 0
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod().createHook {
            returnConstant(ArrayList<String>())
        }

        DexKit.getDexKitBridge("RemoveMacroBlackList2") {
            it.findMethod {
                matcher {
                    returnType = "boolean"
                    addInvoke {
                        addEqString("pref_gb_unsupport_macro_apps")
                        paramCount = 0
                    }
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod().createHook {
            returnConstant(false)
        }

        DexKit.getDexKitBridge("RemoveMacroBlackList3") {
            it.findClass {
                matcher {
                    usingStrings =
                        listOf("content://com.xiaomi.macro.MacroStatusProvider/game_macro_change")
                }
            }.single().getInstance(safeClassLoader)
        }.toClass().apply {
            methodFinder().filterByParamCount(2)
                .toList().createHooks {
                    returnConstant(true)
                }
            methodFinder().filterByParamCount(3)
                .toList().createHooks {
                    returnConstant(true)
                }
        }
    }
}
