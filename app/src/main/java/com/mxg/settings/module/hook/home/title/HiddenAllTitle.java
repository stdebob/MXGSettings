// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import com.mxg.settings.module.base.BaseHook;

public class HiddenAllTitle extends BaseHook {
    @Override
    public void init() {
        /*用于隐藏应用名*/
        findAndHookMethod("com.miui.home.launcher.ItemIcon", "setTitle",
            CharSequence.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(null);
                }
            }
        );
    }
}
