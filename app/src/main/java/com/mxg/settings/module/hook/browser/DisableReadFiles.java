// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.browser;

import android.net.Uri;

import com.mxg.settings.module.base.BaseHook;

public class DisableReadFiles extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.browser.provider.AdBlockRuleProvider", "openFile", Uri.class, String.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });
    }
}
