// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.misettings.CustomRefreshRate;
import com.mxg.settings.module.hook.misettings.ShowMoreFpsList;

@HookExpand(pkg = "com.xiaomi.misettings", isPad = false, tarAndroid = 33)
public class MiSettings extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(CustomRefreshRate.INSTANCE, mPrefsMap.getBoolean("various_custom_refresh_rate"));
        initHook(ShowMoreFpsList.INSTANCE, mPrefsMap.getBoolean("mi_settings_show_fps"));
    }
}
