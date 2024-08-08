// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class InfiniteScroll extends BaseHook {

    @Override
    public void init() {

        findAndHookMethod("com.miui.home.launcher.ScreenView", "getSnapToScreenIndex", int.class, int.class, int.class, new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) throws Throwable {
                if (param.args[0] != param.getResult()) return;
                int screenCount = (int) XposedHelpers.callMethod(param.thisObject, "getScreenCount");
                if ((int) param.args[2] == -1 && (int) param.args[0] == 0)
                    param.setResult(screenCount);
                else if ((int) param.args[2] == 1 && (int) param.args[0] == screenCount - 1)
                    param.setResult(0);
            }
        });

        findAndHookMethod("com.miui.home.launcher.ScreenView", "getSnapUnitIndex", int.class, new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) throws Throwable {
                int index = (int) param.getResult();
                int mCurrentScreenIndex = XposedHelpers.getIntField(param.thisObject, lpparam.packageName.equals("com.miui.home") ? "mCurrentScreenIndex" : "mCurrentScreen");
                if (mCurrentScreenIndex != index) return;
                int screenCount = (int) XposedHelpers.callMethod(param.thisObject, "getScreenCount");
                if (index == 0) {
                    param.setResult(screenCount);
                } else if (index == screenCount - 1) {
                    param.setResult(0);
                }
            }
        });
    }
}
