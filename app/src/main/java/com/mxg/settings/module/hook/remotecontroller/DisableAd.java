// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.remotecontroller;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

public class DisableAd extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.xiaomi.mitv.phone.remotecontroller.common.activity.BaseActivity", "setActionMark", "com.duokan.phone.remotecontroller.operation.Operation", View.OnClickListener.class, new replaceHookedMethod() {
            @Override
            protected Object replace(MethodHookParam param) throws Throwable {
                return null;
            }
        });
    }
}
