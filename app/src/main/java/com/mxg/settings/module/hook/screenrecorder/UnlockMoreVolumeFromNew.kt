// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenrecorder

import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toClass
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import de.robv.android.xposed.*

object UnlockMoreVolumeFromNew : BaseHook() {
    private val getClass by lazy {
        DexKit.getDexKitBridge("UnlockMoreVolumeFromNewClass") {
            it.findClass {
                matcher {
                    addUsingStringsEquals("support_a2dp_inner_record")
                }
            }.single().getInstance(safeClassLoader)
        }.toClass()
    }

    override fun init() {
        val fieldData = DexKit.getDexKitBridgeList("UnlockMoreVolumeFromNewField") { dexkit ->
            dexkit.findField {
                matcher {
                    declaredClass(getClass)
                    type = "boolean"
                }
            }.toElementList()
        }.toFieldList()

        findAndHookConstructor(getClass, object : MethodHook() {
            override fun after(param: MethodHookParam) {
                for (i in fieldData) {
                    XposedHelpers.setObjectField(param.thisObject, i.name, true)
                }
            } })
    }
}
