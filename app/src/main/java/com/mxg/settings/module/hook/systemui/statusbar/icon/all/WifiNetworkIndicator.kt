// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all

import android.view.View
import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers

object WifiNetworkIndicator : BaseHook() {
    var mVisibility = 0
    private var mStatusBarWifiView: Class<*>? = null
    override fun init() {
        mStatusBarWifiView = findClassIfExists("com.android.systemui.statusbar.StatusBarWifiView")
        val mWifiNetworkIndicator =
            mPrefsMap.getStringAsInt("system_ui_status_bar_icon_wifi_network_indicator", 0)

        when (mWifiNetworkIndicator) {
            1 -> mVisibility = View.VISIBLE
            2 -> mVisibility = View.INVISIBLE
        }

        val hideWifiActivity: MethodHook = object : MethodHook() {
            override fun after(param: MethodHookParam) {
                val mWifiActivityView =
                    XposedHelpers.getObjectField(param.thisObject, "mWifiActivityView")
                XposedHelpers.callMethod(mWifiActivityView, "setVisibility", mVisibility)
            }
        }
        hookAllMethods(mStatusBarWifiView, "applyWifiState", hideWifiActivity)
    }
}
