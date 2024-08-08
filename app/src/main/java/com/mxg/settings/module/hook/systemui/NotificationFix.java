// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class NotificationFix extends BaseHook {
    @Override
    public void init() {
        XposedHelpers.setStaticBooleanField(XposedHelpers.findClass("com.android.systemui.statusbar.notification.NotificationSettingsManager", lpparam.classLoader), "USE_WHITE_LISTS", false);
    }
}
