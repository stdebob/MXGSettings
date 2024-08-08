// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all;

import com.mxg.settings.module.base.BaseHook;

public class DataSaverIcon extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.systemui.statusbar.phone.PhoneStatusBarPolicy",
            "onDataSaverChanged",
            boolean.class,
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    int opt = mPrefsMap.getStringAsInt("system_ui_status_bar_icon_data_saver", 0);
                    if (opt == 1) {
                        param.args[0] = true;
                    } else if (opt == 2) {
                        param.args[0] = false;
                    }
                }
            }
        );
    }
}
