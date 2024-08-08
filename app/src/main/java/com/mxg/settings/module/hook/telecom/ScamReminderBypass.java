// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.telecom;

import com.mxg.settings.module.base.BaseHook;

public class ScamReminderBypass extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        hookAllMethods("com.android.server.telecom.MiuiScamReminder", lpparam.classLoader, "isPotentialInCfummi", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                param.setResult("");
            }
        });
    }
}
