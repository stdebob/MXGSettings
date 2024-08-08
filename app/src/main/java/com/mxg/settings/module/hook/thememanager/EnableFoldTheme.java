// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.thememanager;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class EnableFoldTheme extends BaseHook {

    @Override
    public void init() {
        findAndHookMethod("com.android.thememanager.ThemeApplication", "onCreate", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticObjectField(findClassIfExists("android.os.Build"), "DEVICE", "zizhan");
            }
        });

        findAndHookMethod("com.android.thememanager.basemodule.utils.r", "r", XC_MethodReplacement.returnConstant(true));

    }
}
