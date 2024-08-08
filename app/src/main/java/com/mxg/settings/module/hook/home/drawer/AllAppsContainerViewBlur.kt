// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.drawer

import android.app.*
import android.content.*
import android.view.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*
import com.mxg.settings.utils.blur.zhenxiang.*
import com.mxg.settings.utils.blur.zhenxiang.model.*
import com.mxg.settings.utils.devicesdk.*


object AllAppsContainerViewBlur : BaseHook() {
    override fun init() {
        Application::class.java.hookBeforeMethod("attach", Context::class.java) {
            EzXHelper.initHandleLoadPackage(lpparam)
            EzXHelper.initAppContext(it.args[0] as Context)

            loadClass("com.miui.home.launcher.allapps.BaseAllAppsContainerView").methodFinder().filter {
                name == "onFinishInflate"
            }.toList().createHooks {
                after { hookParam ->
                    val mCategoryContainer = hookParam.thisObject.getObjectField("mCategoryContainer") as ViewSwitcher
                    val appsView = mCategoryContainer.parent as RelativeLayout
                    val blur = BlurFrameLayout(mCategoryContainer.context)
                    val radius = getCornerRadiusTop().toFloat()
                    blur.blurController.apply {
                        cornerRadius = CornersRadius.custom(radius, radius, 0f, 0f)
                    }
                    val view = View(mCategoryContainer.context)
                    blur.addView(view)
                    (view.layoutParams as FrameLayout.LayoutParams).apply {
                        width = FrameLayout.LayoutParams.MATCH_PARENT
                        height = FrameLayout.LayoutParams.MATCH_PARENT
                    }
                    appsView.addView(blur, 0)

                    loadClass("com.miui.home.launcher.allapps.BaseAllAppsContainerView").methodFinder().filter {
                        name == "onResume"
                    }.toList().createHooks {
                        after {
                            blur.refreshDrawableState()
                        }
                    }
                }
            }
        }
    }
}
