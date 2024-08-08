// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.lab

import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList

object LabUtilsClass {
    val labUtilClass: MutableList<Class<*>> by lazy {
        DexKit.getDexKitBridgeList("labUtilClass") {
            it.findClass {
                matcher {
                    addUsingStringsEquals("mi_lab_ai_clipboard_enable", "mi_lab_blur_location_enable")
                }
            }.toElementList()
        }.toClassList()
    }
}
