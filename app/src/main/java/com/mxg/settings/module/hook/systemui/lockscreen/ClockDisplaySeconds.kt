// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectFieldAs
import de.robv.android.xposed.XC_MethodHook
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask

object ClockDisplaySeconds : BaseHook() {
    private var nowTime: Date = Calendar.getInstance().time

    override fun init() {
        loadClass("com.miui.clock.MiuiBaseClock").constructorFinder()
            .filterByParamCount(2)
            .single().createHook {
                after {
                    try {
                        val viewGroup = it.thisObject as LinearLayout
                        val d: Method = viewGroup.javaClass.getDeclaredMethod("updateTime")
                        val r = Runnable {
                            d.isAccessible = true
                            d.invoke(viewGroup)
                        }

                        class T : TimerTask() {
                            override fun run() {
                                Handler(viewGroup.context.mainLooper).post(r)
                            }
                        }
                        Timer().schedule(T(), 1000 - System.currentTimeMillis() % 1000, 1000)
                    } catch (_: Exception) {
                    }
                }
            }

        loadClass("com.miui.clock.MiuiLeftTopClock").methodFinder()
            .filterByName("updateTime")
            .single().createHook {
                after { updateTime(it, false) }
            }

        loadClass("com.miui.clock.MiuiCenterHorizontalClock").methodFinder()
            .filterByName("updateTime")
            .single().createHook {
                after { updateTime(it, false) }
            }

        loadClass("com.miui.clock.MiuiLeftTopLargeClock").methodFinder().filterByName("updateTime")
            .single().createHook {
                after { updateTime(it, false) }
            }

        loadClass("com.miui.clock.MiuiVerticalClock").methodFinder().filterByName("updateTime")
            .single().createHook {
                after { updateTime(it, true) }
            }
    }

    private fun updateTime(it: XC_MethodHook.MethodHookParam, isVertical: Boolean) {
        val textV = it.thisObject.getObjectFieldAs<TextView>("mTimeText")
        val c: Context = textV.context

        Log.d("lock_screen_clock_display_seconds", "updateTime: ${it.thisObject.javaClass.simpleName}")
        val is24 = Settings.System.getString(c.contentResolver, Settings.System.TIME_12_24) == "24"

        nowTime = Calendar.getInstance().time

        textV.text = getTime(is24, isVertical)
    }


    @SuppressLint("SimpleDateFormat")
    private fun getTime(is24: Boolean, isVertical: Boolean): String {
        var timePattern = ""
        timePattern += if (isVertical) { // 垂直
            if (is24) "HH\nmm\nss" else "hh\nmm\nss"
        } else { // 水平
            if (is24) "HH:mm:ss" else "h:mm:ss"
        }
        timePattern = SimpleDateFormat(timePattern).format(nowTime)
        return timePattern
    }
}
