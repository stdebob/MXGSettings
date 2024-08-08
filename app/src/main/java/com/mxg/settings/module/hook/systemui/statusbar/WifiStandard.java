// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;

public class WifiStandard extends BaseHook {

    Class<?> mWifiView;
    Class<?> mWifiIconState;

    @Override
    public void init() {

        mWifiView = findClassIfExists("com.android.systemui.statusbar.StatusBarWifiView");
        mWifiIconState = findClassIfExists("com.android.systemui.statusbar.phone.StatusBarSignalPolicy$WifiIconState");


        findAndHookMethod(mWifiView, "applyWifiState", mWifiIconState, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                Object mWifiIconState = param.args[0];
                int mWifiStandard = XposedHelpers.getIntField(mWifiIconState, "wifiStandard");
                if (mWifiIconState != null) {
                    int opt = mPrefsMap.getStringAsInt("system_ui_status_bar_icon_wifi_standard", 0);
                    if (opt == 1) {
                        XposedHelpers.setBooleanField(mWifiIconState, "showWifiStandard", mWifiStandard != 0);
                    } else if (opt == 2) {
                        XposedHelpers.setBooleanField(mWifiIconState, "showWifiStandard", false);
                    }
                }
            }
        });
    }

    private void setWifiStandardIconState(MethodHookParam param) {
        int wifiStandard = XposedHelpers.getIntField(param.thisObject, "wifiStandard");
        int key = mPrefsMap.getStringAsInt("system_ui_status_bar_icon_wifi_standard", 0);
        if (key == 1) {
            XposedHelpers.setBooleanField(param.thisObject, "showWifiStandard", wifiStandard != 0);
        } else if (key == 2) {
            XposedHelpers.setBooleanField(param.thisObject, "showWifiStandard", false);
        }
    }
}
