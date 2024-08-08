// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class ShouldPlayUnmuteSoundHook extends BaseHook {

    Class<?> mQuietModeTile = XposedHelpers.findClassIfExists("com.android.systemui.qs.tiles.QuietModeTile", lpparam.classLoader);
    Class<?> mZenModeController = XposedHelpers.findClassIfExists("com.android.systemui.statusbar.policy.ZenModeController", lpparam.classLoader);

    @Override
    public void init() {
        findAndHookMethod(mQuietModeTile, "handleClick", View.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setBooleanField(mZenModeController, "isZenModeOn", true);
            }
        });
    }
}
