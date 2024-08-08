// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.powerkeeper.CustomRefreshRate;
import com.mxg.settings.module.hook.powerkeeper.DisableGetDisplayCtrlCode;
import com.mxg.settings.module.hook.powerkeeper.DontKillApps;
import com.mxg.settings.module.hook.powerkeeper.LockMaxFps;
import com.mxg.settings.module.hook.powerkeeper.PreventBatteryWitelist;

@HookExpand(pkg = "com.miui.powerkeeper", isPad = false, tarAndroid = 33)
public class PowerKeeper extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new CustomRefreshRate(), mPrefsMap.getBoolean("various_custom_refresh_rate"));
        initHook(new DisableGetDisplayCtrlCode(), mPrefsMap.getBoolean("powerkeeper_disable_get_display_ctrl_code"));
        initHook(LockMaxFps.INSTANCE, mPrefsMap.getBoolean("powerkeeper_lock_max_fps"));
        initHook(DontKillApps.INSTANCE, mPrefsMap.getBoolean("powerkeeper_do_not_kill_apps"));
        initHook(new PreventBatteryWitelist(), mPrefsMap.getBoolean("powerkeeper_prevent_recovery_of_battery_optimization_whitelist"));
    }
}
