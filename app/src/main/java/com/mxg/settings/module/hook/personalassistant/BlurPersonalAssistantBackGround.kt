// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.personalassistant

import android.content.res.Configuration
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethod
import com.mxg.settings.utils.getIntField
import com.mxg.settings.utils.hookBeforeAllMethods
import com.mxg.settings.utils.hookBeforeMethod
import com.mxg.settings.utils.new
import com.mxg.settings.utils.replaceMethod
import com.mxg.settings.utils.setObjectField

object BlurPersonalAssistantBackGround : BaseHook() {
    private val deviceAdapter by lazy {
        loadClass("com.miui.personalassistant.device.DeviceAdapter")
    }
    private val foldableDeviceAdapter by lazy {
        loadClass("com.miui.personalassistant.device.FoldableDeviceAdapter")
    }

    override fun init() {
        deviceAdapter.hookBeforeAllMethods("create") {
            it.result = foldableDeviceAdapter.new(it.args[0])
        }
        try {
            foldableDeviceAdapter.hookBeforeMethod("onEnter", Boolean::class.java) {
                it.thisObject.setObjectField("mScreenSize", 3)
            }
        } catch (e: ClassNotFoundException) {
            foldableDeviceAdapter.hookBeforeMethod("onOpened") {
                it.thisObject.setObjectField("mScreenSize", 3)
            }
        }
        foldableDeviceAdapter.hookBeforeMethod("onConfigurationChanged", Configuration::class.java) {
            it.thisObject.setObjectField("mScreenSize", 3)
        }
        foldableDeviceAdapter.replaceMethod("onScroll", Float::class.java) {
            val f = it.args[0] as Float
            val i = (f * 100.0f).toInt()
            val mCurrentBlurRadius: Int = it.thisObject.getIntField("mCurrentBlurRadius")
            if (mCurrentBlurRadius != i) {
                if (mCurrentBlurRadius <= 0 || i >= 0) {
                    it.thisObject.setObjectField("mCurrentBlurRadius", i)
                } else {
                    it.thisObject.setObjectField("mCurrentBlurRadius", 0)
                }
                it.thisObject.callMethod("blurOverlayWindow", mCurrentBlurRadius)
            }
        }
    }
}
