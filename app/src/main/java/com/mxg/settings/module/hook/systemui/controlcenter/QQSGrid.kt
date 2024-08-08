// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter

import android.content.res.Configuration
import android.view.ViewGroup

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

import com.mxg.settings.module.base.BaseHook

class QQSGrid : BaseHook() {
    override fun init() {
        val cols = mPrefsMap.getInt("system_control_center_old_qs_grid_columns", 5)
        val colsHorizontal = mPrefsMap.getInt("system_control_center_old_qs_grid_columns_horizontal", 6)

        loadClass("com.android.systemui.qs.MiuiQuickQSPanel").methodFinder()
            .filterByName("setMaxTiles")
            .filterByParamCount(1)
            .single().createHook {
                before {
                    val viewGroup = it.thisObject as ViewGroup
                    val mConfiguration: Configuration = viewGroup.context.resources.configuration
                    if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        it.args[0] = cols
                    } else {
                        it.args[0] = colsHorizontal
                    }
                }
            }
    }
}
