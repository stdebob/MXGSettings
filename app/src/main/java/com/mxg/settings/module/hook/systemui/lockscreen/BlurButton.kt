// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.app.*
import android.content.*
import android.graphics.*
import android.graphics.drawable.*
import android.view.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.blur.BlurUtils.*
import com.mxg.settings.utils.devicesdk.*
import de.robv.android.xposed.*

object BlurButton : BaseHook() {
    private val removeLeft by lazy {
        mPrefsMap.getBoolean("system_ui_lock_screen_hide_smart_screen")
    }
    private val removeRight by lazy {
        mPrefsMap.getBoolean("system_ui_lock_screen_hide_camera")
    }

    override fun init() {
        // by StarVoyager
        if (isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34)) {
            loadClassOrNull(
                "com.android.keyguard.injector.KeyguardBottomAreaInjector"
            )!!.methodFinder()
                .filter {
                    name in setOf(
                        "updateLeftIcon",
                        "updateRightIcon"
                    )
                }.toList().createHooks {
                    after { param ->
                        systemBlur(param)
                    }
                }
        } else {
            loadClassOrNull(
                "com.android.systemui.statusbar.phone.KeyguardBottomAreaView"
            )!!.methodFinder()
                .filter {
                    name in setOf(
                        "onAttachedToWindow",
                        "onDetachedFromWindow",
                        "updateRightAffordanceIcon",
                        "updateLeftAffordanceIcon"
                    )
                }.toList().createHooks {
                    after { param ->
                        systemBlur(param)
                    }
                }
        }
    }

    private fun setNewBackgroundBlur(imageView: ImageView): LayerDrawable {
        val blurDrawable = createBlurDrawable(
            imageView, 40, 100, Color.argb(60, 255, 255, 255)
        )
        val layoutDrawable = LayerDrawable(arrayOf(blurDrawable))
        layoutDrawable.setLayerInset(0, 40, 40, 40, 40)
        return layoutDrawable
    }

    private fun setOldBackgroundBlur(view: View): LayerDrawable {
        val blurDrawable = createBlurDrawable(
            view, 40, 100, Color.argb(60, 255, 255, 255)
        )
        val layoutDrawable = LayerDrawable(arrayOf(blurDrawable))
        layoutDrawable.setLayerInset(0, 40, 40, 40, 40)
        return layoutDrawable
    }

    private fun systemBlur(param: XC_MethodHook.MethodHookParam) {
        if (isMoreHyperOSVersion(1f)) {
            val mLeftAffordanceView: ImageView = ObjectUtils.getObjectOrNullAs<ImageView>(
                param.thisObject,
                "mLeftButton"
            )!!
            val mRightAffordanceView: ImageView = ObjectUtils.getObjectOrNullAs<ImageView>(
                param.thisObject,
                "mRightButton"
            )!!
            // Your blur logic
            val context = ObjectUtils.getObjectOrNull(param.thisObject, "mContext") as Context
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (keyguardManager.isKeyguardLocked) {
                mLeftAffordanceView.background = if (!removeLeft) {
                    setNewBackgroundBlur(mLeftAffordanceView)
                } else null
                mRightAffordanceView.background = if (!removeRight) {
                    setNewBackgroundBlur(mRightAffordanceView)
                } else null
            } else {
                mLeftAffordanceView.background = null
                mRightAffordanceView.background = null
            }
        } else {
            val mLeftAffordanceView: ImageView =
                ObjectUtils.getObjectOrNullAs<ImageView>(
                    param.thisObject,
                    "mLeftAffordanceView"
                )!!

            val mRightAffordanceView: ImageView =
                ObjectUtils.getObjectOrNullAs<ImageView>(
                    param.thisObject,
                    "mRightAffordanceView"
                )!!

            val keyguardBottomAreaView: View = param.thisObject as View
            // Your blur logic
            val context = keyguardBottomAreaView.context
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (keyguardManager.isKeyguardLocked) {
                mLeftAffordanceView.background = if (!removeLeft) {
                    setOldBackgroundBlur(keyguardBottomAreaView)
                } else null
                mRightAffordanceView.background =  if (!removeRight) {
                    setOldBackgroundBlur(keyguardBottomAreaView)
                } else null
            } else {
                mLeftAffordanceView.background = null
                mRightAffordanceView.background = null
            }
        }
    }

    /*private fun miuiBlur(param: XC_MethodHook.MethodHookParam) {

    }*/
}
