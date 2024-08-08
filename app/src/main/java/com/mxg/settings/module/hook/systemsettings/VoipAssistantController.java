// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

public class VoipAssistantController extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.lab.MiuiVoipAssistantController", "isNotSupported", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });

    }
}
