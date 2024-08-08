// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import com.mxg.settings.module.base.BaseHook;

public class MiuiGxzwSize extends BaseHook {

    @Override
    public void init() {

        Class<?> mMiuiGxzwUtils = findClassIfExists("com.android.keyguard.fod.MiuiGxzwUtils");

        /*hookAllMethods(mMiuiGxzwUtils,"caculateGxzwIconSize", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticIntField(mMiuiGxzwUtils,"GXZW_ANIM_HEIGHT", 1028);
                XposedHelpers.setStaticIntField(mMiuiGxzwUtils,"GXZW_ANIM_WIDTH", 1028);
            }
        });*/
    }
}
