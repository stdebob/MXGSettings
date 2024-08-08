// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all

import android.view.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*

object HideBatteryIcon : BaseHook() {
    override fun init() {
        val mBatteryMeterViewClass by lazy {
            loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterView")
        }

        try {
            mBatteryMeterViewClass.methodFinder()
                .filterByName("updateResources")
                .single()
        } catch (t: Throwable) {
            mBatteryMeterViewClass.methodFinder()
                .filterByName("updateAll")
                .single()
        }.createHook {
            after { param ->
                if (param.thisObject != null) {
                    // 隐藏电池图标
                    if (mPrefsMap.getBoolean("system_ui_status_bar_battery_icon")) {
                        (param.thisObject.getObjectFieldAs<ImageView>("mBatteryIconView")).visibility =
                            View.GONE

                        if (param.thisObject.getObjectField("mBatteryStyle") == 1) {
                            (param.thisObject.getObjectFieldAs<FrameLayout>("mBatteryDigitalView")).visibility =
                                View.GONE
                        }
                    }
                    // 隐藏电池百分号
                    if (mPrefsMap.getBoolean("system_ui_status_bar_battery_percent") ||
                        mPrefsMap.getBoolean("system_ui_status_bar_battery_percent_mark")
                    ) {
                        (param.thisObject?.getObjectFieldAs<TextView>("mBatteryPercentMarkView"))?.textSize = 0F
                    }
                    // 隐藏电池内的百分比
                    if (mPrefsMap.getBoolean("system_ui_status_bar_battery_percent")) {
                        (param.thisObject?.getObjectFieldAs<TextView>("mBatteryPercentView"))?.textSize = 0F
                        (param.thisObject?.getObjectFieldAs<TextView>("mBatteryTextDigitView"))?.textSize = 0F
                    }
                }
            }
        }

        mBatteryMeterViewClass.methodFinder()
            .filterByName("updateChargeAndText")
            .single().createHook {
                after { param ->
                    // 隐藏电池充电图标
                    if (param.thisObject != null) {
                        if (mPrefsMap.getBoolean("system_ui_status_bar_battery_charging")) {
                            (param.thisObject.getObjectFieldAs<ImageView>("mBatteryChargingInView")).visibility =
                                View.GONE
                            (param.thisObject.getObjectFieldAs<ImageView>("mBatteryChargingView")).visibility =
                                View.GONE
                        }
                    }
                }
            }
    }

}
