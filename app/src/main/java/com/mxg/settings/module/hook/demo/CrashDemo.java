// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.demo;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class CrashDemo extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        XposedHelpers.findAndHookMethod("com.hchen.demo.MainActivity", lpparam.classLoader,
            "crash", int.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    int o = (int) param.args[0];
                    param.args[0] = 0;
                    logE(TAG, "int: " + o);
                }
            }
        );
    }
}
