// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import android.content.pm.LauncherActivityInfo;

import com.mxg.settings.module.base.BaseHook;

public class PerfectIcon extends BaseHook {

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.library.compat.LauncherActivityInfoCompat", "getIconResource", LauncherActivityInfo.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(0);
            }
        });
    }
}
