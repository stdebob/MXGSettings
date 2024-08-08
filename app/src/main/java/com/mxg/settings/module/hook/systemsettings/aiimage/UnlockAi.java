// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings.aiimage;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

public class UnlockAi extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "isAiSupport", Context.class, new BaseHook.MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
    }
}
