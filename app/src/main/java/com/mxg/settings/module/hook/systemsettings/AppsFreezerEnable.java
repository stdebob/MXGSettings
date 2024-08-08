// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

public class AppsFreezerEnable extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.development.CachedAppsFreezerPreferenceController",
            lpparam.classLoader,
            "isAvailable",
            new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    super.after(param);
                    param.setResult(true);
                }
            }
        );
    }
}
