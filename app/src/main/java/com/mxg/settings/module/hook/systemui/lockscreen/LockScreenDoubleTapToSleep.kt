// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.app.KeyguardManager
import android.content.Context
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isMoreAndroidVersion
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.getAdditionalInstanceField
import de.robv.android.xposed.XposedHelpers.setAdditionalInstanceField

object LockScreenDoubleTapToSleep : BaseHook() {
    private val className by lazy {
        if (isMoreAndroidVersion(34)) {
            loadClass("com.android.systemui.shade.NotificationsQuickSettingsContainer")
        } else {
            loadClass("com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer")
        }
    }

    override fun init() {
        className.methodFinder()
            .filterByName("onFinishInflate")
            .single().createHook {
                before {
                    val view = it.thisObject as View
                    setAdditionalInstanceField(view, "currentTouchTime", 0L)
                    setAdditionalInstanceField(view, "currentTouchX", 0f)
                    setAdditionalInstanceField(view, "currentTouchY", 0f)
                    view.setOnTouchListener(View.OnTouchListener { v, event ->
                        if (event.action != MotionEvent.ACTION_DOWN) return@OnTouchListener false

                        var currentTouchTime =
                            getAdditionalInstanceField(view, "currentTouchTime") as Long
                        var currentTouchX =
                            getAdditionalInstanceField(view, "currentTouchX") as Float
                        var currentTouchY =
                            getAdditionalInstanceField(view, "currentTouchY") as Float
                        val lastTouchTime = currentTouchTime
                        val lastTouchX = currentTouchX
                        val lastTouchY = currentTouchY

                        currentTouchTime = System.currentTimeMillis()
                        currentTouchX = event.x
                        currentTouchY = event.y

                        if (currentTouchTime - lastTouchTime < 250L
                            && kotlin.math.abs(currentTouchX - lastTouchX) < 100f
                            && kotlin.math.abs(currentTouchY - lastTouchY) < 100f
                        ) {
                            val keyguardMgr =
                                v.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

                            if (keyguardMgr.isKeyguardLocked) {
                                XposedHelpers.callMethod(
                                    v.context.getSystemService(Context.POWER_SERVICE),
                                    "goToSleep",
                                    SystemClock.uptimeMillis()
                                )
                            }
                            currentTouchTime = 0L
                            currentTouchX = 0f
                            currentTouchY = 0f
                        }

                        setAdditionalInstanceField(view, "currentTouchTime", currentTouchTime)
                        setAdditionalInstanceField(view, "currentTouchX", currentTouchX)
                        setAdditionalInstanceField(view, "currentTouchY", currentTouchY)
                        v.performClick()
                        false
                    })
                }
            }
    }

}
