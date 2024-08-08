// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater

import android.os.Build
import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers

object AndroidVersionCode : BaseHook() {
    private val mAndroidVersionCode =
        mPrefsMap.getString("various_updater_android_version", "14")

    override fun init() {
        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "RELEASE", mAndroidVersionCode)
    }
}
