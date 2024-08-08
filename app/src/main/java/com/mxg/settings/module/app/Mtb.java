// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.mtb.BypassAuthentication;
import com.mxg.settings.module.hook.mtb.IsUserBuild;

@HookExpand(pkg = "com.xiaomi.mtb", isPad = false, tarAndroid = 33)
public class Mtb extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(BypassAuthentication.INSTANCE, mPrefsMap.getBoolean("mtb_auth"));
        initHook(IsUserBuild.INSTANCE, mPrefsMap.getBoolean("mtb_auth"));
    }
}
