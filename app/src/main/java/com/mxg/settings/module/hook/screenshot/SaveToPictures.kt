// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenshot

import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers

object SaveToPictures : BaseHook() {
    override fun init() {
        val clazz = XposedHelpers.findClass("android.os.Environment", lpparam.classLoader)
        XposedHelpers.setStaticObjectField(clazz, "DIRECTORY_DCIM", "Pictures")
    }
}
