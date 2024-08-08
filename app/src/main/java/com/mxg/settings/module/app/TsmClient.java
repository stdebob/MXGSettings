// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.tsmclient.AutoNfc;

@HookExpand(pkg = "com.miui.tsmclient", isPad = false, tarAndroid = 33)
public class TsmClient extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(AutoNfc.INSTANCE, mPrefsMap.getBoolean("tsmclient_auto_nfc"));
    }
}
