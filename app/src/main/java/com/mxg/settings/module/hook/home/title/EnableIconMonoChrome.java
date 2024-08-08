// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import com.mxg.settings.module.base.BaseHook;

public class EnableIconMonoChrome extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.graphics.MonochromeUtils", "isSupportMonochrome", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
