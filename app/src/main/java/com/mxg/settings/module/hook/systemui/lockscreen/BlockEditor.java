// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen;

import com.mxg.settings.module.base.BaseHook;

public class BlockEditor extends BaseHook {
    Class<?> mKeyguardEditorHelperCls;

    @Override
    public void init() {

        mKeyguardEditorHelperCls = findClassIfExists("com.android.keyguard.KeyguardEditorHelper");
        findAndHookMethod(mKeyguardEditorHelperCls, "checkIfStartEditActivity", new replaceHookedMethod() {
            @Override
            protected Object replace(MethodHookParam param) throws Throwable {
                return null;
            }
        });
    }
}
