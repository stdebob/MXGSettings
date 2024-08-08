package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

public class DisableInstallUnknownVerify extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.settings.applications.appinfo.ExternalSourcesDetails", "doUnknownSourceVerify", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
    }
}
