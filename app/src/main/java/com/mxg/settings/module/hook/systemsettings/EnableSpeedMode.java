// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

public class EnableSpeedMode extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.development.SpeedModeToolsPreferenceController", "getAvailabilityStatus", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(0);
            }
        });

    }
}
