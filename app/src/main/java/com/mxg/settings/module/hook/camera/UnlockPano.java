// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import com.mxg.settings.module.base.BaseHook;

public class UnlockPano extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.android.camera.features.mode.pano.pano3.PanoModuleEntry", "support", new BaseHook.MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
    }
}
