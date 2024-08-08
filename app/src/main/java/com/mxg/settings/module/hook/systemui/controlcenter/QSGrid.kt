// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter

import android.content.res.*
import android.view.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.devicesdk.*
import de.robv.android.xposed.*

class QSGrid : BaseHook() {
    private val miuiTileClass by lazy {
        loadClass("com.android.systemui.qs.MiuiTileLayout")
    }

    override fun init() {
        val cols = mPrefsMap.getInt("system_control_center_old_qs_columns", 4)
        val colsHorizontal = mPrefsMap.getInt("system_control_center_old_qs_columns_horizontal", 5)
        val rows = mPrefsMap.getInt("system_control_center_old_qs_rows", 3)
        val rowsHorizontal = mPrefsMap.getInt("system_control_center_old_qs_rows_horizontal", 2)

        if (isMoreHyperOSVersion(1f) && isAndroidVersion(34)) {
            hyperHooks(cols, colsHorizontal)
        } else {
            miuiHooks(cols, colsHorizontal)
        }

        miuiTileClass.methodFinder()
            .filterByName("updateResources")
            .first().createHook {
                after {
                    val viewGroup = it.thisObject as ViewGroup
                    val mConfiguration: Configuration = viewGroup.context.resources.configuration
                    if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        XposedHelpers.setObjectField(it.thisObject, "mMaxAllowedRows", rows)
                    } else {
                        XposedHelpers.setObjectField(it.thisObject, "mMaxAllowedRows", rowsHorizontal)
                    }
                    viewGroup.requestLayout()
                }
            }
    }

    private fun hyperHooks(
        cols: Int,
        colsHorizontal: Int
    ) {
        miuiTileClass.methodFinder()
            .filterByName("layoutTileRecords")
            .filterByParamCount(1)
            .first().createHook {
                after {
                    val viewGroup = it.thisObject as ViewGroup
                    val mConfiguration: Configuration = viewGroup.context.resources.configuration
                    if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        XposedHelpers.setObjectField(it.thisObject, "mColumns", cols)
                    } else {
                        XposedHelpers.setObjectField(it.thisObject, "mColumns", colsHorizontal)
                    }
                }
            }
    }

    private fun miuiHooks(
        cols: Int,
        colsHorizontal: Int
    ) {
        miuiTileClass.methodFinder()
            .filterByName("updateColumns")
            .first().createHook {
                after {
                    val viewGroup = it.thisObject as ViewGroup
                    val mConfiguration: Configuration = viewGroup.context.resources.configuration
                    if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        XposedHelpers.setObjectField(it.thisObject, "mColumns", cols)
                    } else {
                        XposedHelpers.setObjectField(it.thisObject, "mColumns", colsHorizontal)
                    }
                }
            }
    }
}
