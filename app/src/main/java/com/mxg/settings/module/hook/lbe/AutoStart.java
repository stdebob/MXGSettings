// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.lbe;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

public class AutoStart extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.privacy.autostart.AutoRevokePermissionManager",
                "lambda$startScheduleASCheck$1",
                Context.class, boolean.class,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(null);
                    }
                }
        );
    }
}
