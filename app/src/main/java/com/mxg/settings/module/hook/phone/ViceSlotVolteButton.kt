// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.phone

import android.provider.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.tool.OtherTool.*

object ViceSlotVolteButton : BaseHook() {
    override fun init() {
        runCatching {
            // exec("settings put global vice_slot_volte_data_enabled 1")
            Settings.Global.putInt(
                findContext(FlAG_ONLY_ANDROID).contentResolver,
                "vice_slot_volte_data_enabled",
                1
            )
            loadClass("com.android.phone.MiuiPhoneUtils").methodFinder()
                .filterByName("shouldHideViceSlotVolteDataButton")
                .single().createHook {
                    returnConstant(false)
                }
        }
        runCatching {
            loadClass("com.android.phone.MiuiPhoneUtils").methodFinder()
                .filterByName("shouldHideSmartDualSimButton")
                .single().createHook {
                    returnConstant(false)
                }
        }
    }
}
