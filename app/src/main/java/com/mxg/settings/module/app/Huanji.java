// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.huanji.AllowMoveAllApps;

@HookExpand(pkg = "com.miui.huanji", isPad = false, tarAndroid = 33)
public class Huanji extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new AllowMoveAllApps(), mPrefsMap.getBoolean("huanji_allow_all_apps"));
    }
}
