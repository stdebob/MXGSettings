// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater

import com.github.kyuubiran.ezxhelper.*
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.*
import org.luckypray.dexkit.query.enums.*

object AutoUpdateDialog : BaseHook() {
    private val find1 by lazy {
        DexKit.getDexKitBridge("AutoUpdateDialog1") {
            it.findMethod {
                matcher {
                    addCall {
                        addUsingString("isShowAutoSetDialog", StringMatchType.Contains)
                    }
                    paramTypes("boolean", "boolean")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    private val find2 by lazy {
        DexKit.getDexKitBridge("AutoUpdateDialog2") {
            it.findMethod {
                matcher {
                    addCall {
                        addUsingString("isShowMobileDownloadDialog", StringMatchType.Contains)
                    }
                    paramTypes("long", "int")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    override fun init() {
        logD(TAG, lpparam.packageName, "get find1 is $find1")
        logD(TAG, lpparam.packageName, "get find2 is $find2")
        setOf(find1, find2).forEach {
            it.replaceMethod { 0 }
        }
    }
}
