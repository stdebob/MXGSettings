// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.nfc.AllowInformationScreen;
import com.mxg.settings.module.hook.nfc.DisableSound;

@HookExpand(pkg = "com.android.nfc", isPad = false, tarAndroid = 33)
public class Nfc extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DisableSound(), mPrefsMap.getBoolean("nfc_disable_sound"));
        initHook(new AllowInformationScreen(), mPrefsMap.getBoolean("nfc_allow_information_screen"));
    }
}
