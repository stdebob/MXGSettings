// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.milink.AllowCameraDevices;
import com.mxg.settings.module.hook.milink.FuckHpplay;
import com.mxg.settings.module.hook.milink.UnlockHMind;
import com.mxg.settings.module.hook.milink.UnlockMiShare;

@HookExpand(pkg = "com.milink.service", isPad = false, tarAndroid = 33)
public class MiLink extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnlockMiShare(), mPrefsMap.getBoolean("milink_unlock_mishare"));
        initHook(new UnlockHMind(), mPrefsMap.getBoolean("milink_unlock_hmind"));
        initHook(new AllowCameraDevices(), mPrefsMap.getBoolean("milink_allow_camera_devices"));
        initHook(new FuckHpplay(), mPrefsMap.getBoolean("milink_fuck_hpplay"));
    }
}
