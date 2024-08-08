// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.demo.ColorTest;
import com.mxg.settings.module.hook.demo.CrashDemo;
import com.mxg.settings.module.hook.demo.ToastTest;

@HookExpand(pkg = "com.hchen.demo", isPad = false, tarAndroid = 33)
public class Demo extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new ToastTest(), true);
        initHook(new CrashDemo(), true);
        initHook(new ColorTest(), true);
    }
}
