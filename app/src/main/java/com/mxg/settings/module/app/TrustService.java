// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.trustservice.DisableMrm;

@HookExpand(pkg = "com.xiaomi.trustservice", isPad = false, tarAndroid = 33)
public class TrustService extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(new DisableMrm(), mPrefsMap.getBoolean("trustservice_disable_mrm"));
    }
}
