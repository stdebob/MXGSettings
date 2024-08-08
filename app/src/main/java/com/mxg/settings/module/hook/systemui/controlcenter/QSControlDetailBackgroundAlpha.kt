// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter

import android.graphics.drawable.Drawable
import android.view.View
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getValueByField
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object QSControlDetailBackgroundAlpha : BaseHook() {
    override fun init() {
        val qSControlDetailBackgroundAlpha =
            mPrefsMap.getInt("system_ui_control_center_control_detail_background_alpha", 255)
        val qSControlDetailClass = findClassIfExists(
            "com.android.systemui.controlcenter.phone.detail.QSControlDetail"
        )
        if (qSControlDetailClass != null) {
            XposedHelpers.findAndHookMethod(
                qSControlDetailClass,
                "updateBackground",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val mDetailContainer =
                            getValueByField(param.thisObject, "mDetailContainer") as View
                        if (mDetailContainer.background != null) {
                            val smoothRoundDrawable = mDetailContainer.background
                            smoothRoundDrawable.alpha = qSControlDetailBackgroundAlpha
                        }
                    }
                })
        }
        val modalQSControlDetailClass = findClassIfExists(
            "com.android.systemui.statusbar.notification.modal.ModalQSControlDetail"
        )
        if (modalQSControlDetailClass != null) {
            XposedHelpers.findAndHookMethod(
                modalQSControlDetailClass,
                "updateBackground",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val mDetailContainer =
                            getValueByField(param.thisObject, "mDetailContainer") as View
                        if (mDetailContainer.background != null) {
                            val smoothRoundDrawable = mDetailContainer.background
                            smoothRoundDrawable.alpha = qSControlDetailBackgroundAlpha
                        }
                    }
                })
        }

        hookClassInPlugin { classLoader ->
            try {
                val smoothRoundDrawableClass = XposedHelpers.callMethod(
                    classLoader,
                    "loadClass",
                    "miui.systemui.widget.SmoothRoundDrawable"
                ) ?: return@hookClassInPlugin
                XposedBridge.hookAllMethods(
                    smoothRoundDrawableClass as Class<*>,
                    "inflate",
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            try {
                                val currentDrawable = param.thisObject as Drawable
                                currentDrawable.alpha = qSControlDetailBackgroundAlpha
                            } catch (e: Throwable) {
                                // Do Nothings.
                                logW(TAG, this@QSControlDetailBackgroundAlpha.lpparam.packageName, e)
                            }
                        }
                    })
            } catch (e: Throwable) {
                logW(TAG, this.lpparam.packageName, e)
            }
        }
    }

    private fun hookClassInPlugin(afterGetClassLoader: (classLoader: ClassLoader) -> Unit) {
        val pluginHandlerClass = findClassIfExists(
            "com.android.systemui.shared.plugins.PluginInstanceManager\$PluginHandler"
        )
        if (pluginHandlerClass != null) {
            XposedBridge.hookAllMethods(
                pluginHandlerClass,
                "handleLoadPlugin",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val componentName = param.args[0]
                        val className =
                            XposedHelpers.callMethod(componentName, "getClassName") as String
                        if (className != "miui.systemui.volume.VolumeDialogPlugin") {
                            return
                        }
                        try {
                            val pluginContextWrapper =
                                getValueByField(param.result ?: return, "mPluginContext") ?: return
                            val classLoader = XposedHelpers.callMethod(
                                pluginContextWrapper,
                                "getClassLoader"
                            ) as ClassLoader
                            afterGetClassLoader(classLoader)
                        } catch (e: Throwable) {
                            // Do Nothings.
                            logW(TAG, this@QSControlDetailBackgroundAlpha.lpparam.packageName, e)
                        }
                    }
                })
            return
        }

        val pluginActionManagerClass = findClassIfExists(
            "com.android.systemui.shared.plugins.PluginActionManager"
        )
        if (pluginActionManagerClass != null) {
            XposedBridge.hookAllMethods(
                pluginActionManagerClass,
                "loadPluginComponent",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val componentName = param.args[0]
                        val className =
                            XposedHelpers.callMethod(componentName, "getClassName") as String
                        if (className != "miui.systemui.volume.VolumeDialogPlugin") {
                            return
                        }
                        try {
                            val pluginContextWrapper =
                                getValueByField(param.result ?: return, "mPluginContext")
                                    ?: return
                            val classLoader = XposedHelpers.callMethod(
                                pluginContextWrapper,
                                "getClassLoader"
                            ) as ClassLoader
                            afterGetClassLoader(classLoader)
                        } catch (e: Throwable) {
                            // Do Nothings.
                            logW(TAG, this@QSControlDetailBackgroundAlpha.lpparam.packageName, e)
                        }
                    }
                })
            return
        }
    }
}
