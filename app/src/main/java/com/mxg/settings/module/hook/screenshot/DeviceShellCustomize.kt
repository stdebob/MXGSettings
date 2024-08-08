// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenshot

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object DeviceShellCustomize : BaseHook() {
     private lateinit var device: String
     private val deviceS by lazy {
         mPrefsMap.getString("screenshot_device_customize", "")
     }

     override fun init() {
         loadClass("com.miui.gallery.editor.photo.screen.shell.res.ShellResourceFetcher").methodFinder()
             .filterByName("getResId")
             .first().createHook {
                 before {
                     if (!this@DeviceShellCustomize::device.isInitialized) {
                         device = Build.DEVICE
                     }
                     setStaticObject(loadClass("android.os.Build"), "DEVICE", deviceS)
                 }

                 after {
                     setStaticObject(loadClass("android.os.Build"), "DEVICE", device)
                 }
             }
     }
}
