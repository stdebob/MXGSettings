// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import com.mxg.settings.module.base.BaseHook;

public class NewInstallIndicator extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.TitleTextView",
            "updateNewInstallIndicator",
            boolean.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(null);
                }
            }
        );
    }
}
