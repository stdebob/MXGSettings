// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.getapps.DeviceModify;
import com.mxg.settings.module.hook.getapps.DisableAds;
import com.mxg.settings.module.hook.getapps.DisablePackageMonitor;

@HookExpand(pkg = "com.xiaomi.market", isPad = false, tarAndroid = 33)
public class GetApps extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DisableAds(), mPrefsMap.getBoolean("market_disable_ads"));
        initHook(new DeviceModify(), mPrefsMap.getStringAsInt("market_device_modify_new", 0) != 0);

        initHook(DisablePackageMonitor.INSTANCE, mPrefsMap.getBoolean("market_package_monitor"));
    }
}
