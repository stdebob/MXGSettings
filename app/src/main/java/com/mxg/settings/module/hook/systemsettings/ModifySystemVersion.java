// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

public class ModifySystemVersion extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.device.MiuiAboutPhoneUtils", "getOsVersionCode", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(mPrefsMap.getString("various_updater_miui_version", "1.0.0.0"));
            }
        });
    }
}
