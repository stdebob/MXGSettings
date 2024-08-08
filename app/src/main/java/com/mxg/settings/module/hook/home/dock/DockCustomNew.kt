// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock

import android.app.*
import android.view.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*
import com.mxg.settings.utils.blur.MiBlurUtilsKt.addMiBackgroundBlendColor
import com.mxg.settings.utils.blur.MiBlurUtilsKt.clearMiBackgroundBlendColor
import com.mxg.settings.utils.blur.MiBlurUtilsKt.setBlurRoundRect
import com.mxg.settings.utils.blur.MiBlurUtilsKt.setMiBackgroundBlurMode
import com.mxg.settings.utils.blur.MiBlurUtilsKt.setMiBackgroundBlurRadius
import com.mxg.settings.utils.blur.MiBlurUtilsKt.setMiViewBlurMode
import com.mxg.settings.utils.blur.MiBlurUtilsKt.setPassWindowBlurEnabled
import com.mxg.settings.utils.devicesdk.DisplayUtils.*
import de.robv.android.xposed.*

object DockCustomNew : BaseHook() {
    private val launcherClass by lazy {
        loadClass("com.miui.home.launcher.Launcher")
    }

    override fun init() {
        launcherClass.constructors.toList().createHooks {
            after {
                val context = AndroidAppHelper.currentApplication().applicationContext
                var mDockBlur = XposedHelpers.getAdditionalInstanceField(it.thisObject, "mDockBlur")
                if (mDockBlur != null) return@after
                mDockBlur = FrameLayout(context)
                XposedHelpers.setAdditionalInstanceField(it.thisObject, "mDockBlur", mDockBlur)
            }
        }

        launcherClass.hookAfterMethod("setupViews") {
            val mHotSeats = it.thisObject.getObjectField("mHotSeats") as FrameLayout
            val mDockBlur =
                XposedHelpers.getAdditionalInstanceField(it.thisObject, "mDockBlur") as FrameLayout
            val mDockRadius =
                dp2px(mPrefsMap.getInt("home_dock_bg_radius", 30).toFloat())
            val mDockHeight =
                dp2px(mPrefsMap.getInt("home_dock_bg_height", 80).toFloat())
            val mDockMargin = dp2px(
                (mPrefsMap.getInt("home_dock_bg_margin_horizontal", 30) - 6).toFloat()
            )
            val mDockBottomMargin = dp2px(
                (mPrefsMap.getInt("home_dock_bg_margin_bottom", 30) - 92).toFloat()
            )
            if (mPrefsMap.getStringAsInt("home_dock_add_blur", 0) == 1) {
                mDockBlur.setPassWindowBlurEnabled(true)
                mDockBlur.setMiBackgroundBlurMode(1) //非0时截断
                mDockBlur.setMiBackgroundBlurRadius(
                    mPrefsMap.getInt(
                    "custom_background_blur_degree",
                    200
                    )
                )
                mDockBlur.clearMiBackgroundBlendColor()
                mDockBlur.addMiBackgroundBlendColor(mPrefsMap.getInt("home_dock_bg_color", 0), 101)
                mDockBlur.setMiViewBlurMode(1)
                mDockBlur.setMiBackgroundBlurMode(0)
            }
            val mAllApp = mPrefsMap.getBoolean("home_dock_bg_all_app")
            mDockBlur.setBlurRoundRect(mDockRadius)
            if (mPrefsMap.getStringAsInt("home_dock_add_blur", 0) == 0) mDockBlur.setBackgroundColor(mPrefsMap.getInt("home_dock_bg_color", 0))
            mDockBlur.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mDockHeight)
                    .also { layoutParams ->
                        if (mAllApp) layoutParams.gravity = Gravity.TOP
                        else layoutParams.gravity = Gravity.BOTTOM
                        layoutParams.setMargins(mDockMargin, 0, mDockMargin, mDockBottomMargin)
                    }
            mHotSeats.addView(mDockBlur, 0)
        }

    }
}
