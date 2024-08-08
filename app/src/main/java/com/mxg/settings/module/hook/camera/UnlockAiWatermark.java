// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import com.mxg.settings.module.base.BaseHook;

public class UnlockAiWatermark extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.android.camera.features.mode.aiwatermark.AIWaterModuleEntry", "support", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
