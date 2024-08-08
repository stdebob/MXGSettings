// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.tool.HookTool;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class AllowManageAllNotifications implements IXposedHookZygoteInit  {
    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws NoSuchMethodException {

        XposedHelpers.findAndHookMethod("android.app.NotificationChannel", startupParam.getClass().getClassLoader(), "isBlockable", HookTool.MethodHook.returnConstant(true));

        XposedHelpers.findAndHookMethod("android.app.NotificationChannel", startupParam.getClass().getClassLoader(), "setBlockable", boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = true;
            }
        });

    }
}
