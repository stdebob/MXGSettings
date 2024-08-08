// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.milink;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;

public class UnlockMiShare extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.miui.circulate.world.auth.AuthUtil", "doPermissionCheck", new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) {
                param.setResult(null);
            }
        });
    }
}
