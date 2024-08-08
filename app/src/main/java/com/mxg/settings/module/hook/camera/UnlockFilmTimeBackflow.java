// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import com.mxg.settings.module.base.BaseHook;

public class UnlockFilmTimeBackflow extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.android.camera.features.mode.film.timebackflow.TimeBackflowModuleEntry", "support", new BaseHook.MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
