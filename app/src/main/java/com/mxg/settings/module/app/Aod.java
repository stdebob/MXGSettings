// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.aod.UnlockAlwaysOnDisplay;
import com.mxg.settings.module.hook.aod.UnlockAodAon;

@HookExpand(pkg = "com.miui.aod", isPad = false, tarAndroid = 33)
public class Aod extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(UnlockAlwaysOnDisplay.INSTANCE, mPrefsMap.getBoolean("aod_unlock_always_on_display"));
        initHook(new UnlockAodAon(), mPrefsMap.getBoolean("aod_unlock_aon"));
    }
}
