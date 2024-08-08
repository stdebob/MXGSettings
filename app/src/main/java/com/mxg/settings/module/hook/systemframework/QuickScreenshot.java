// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

public class QuickScreenshot extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.server.policy.PhoneWindowManager", "getScreenshotChordLongPressDelay", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(0L);
            }
        });
    }
}
