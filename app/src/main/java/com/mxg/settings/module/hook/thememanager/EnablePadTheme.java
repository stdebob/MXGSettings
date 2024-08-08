// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.thememanager;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class EnablePadTheme extends BaseHook {

    @Override
    public void init() {

        /*findAndHookMethod("com.android.thememanager.basemodule.utils.r", "C", XC_MethodReplacement.returnConstant(true));
        findAndHookMethod("com.android.thememanager.basemodule.utils.r", "D", XC_MethodReplacement.returnConstant(true));*/


        findAndHookMethod("com.android.thememanager.ThemeApplication", "onCreate", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_TABLET", true);
            }
        });

        /*findAndHookMethod("com.android.thememanager.basemodule.utils.r", "r", XC_MethodReplacement.returnConstant(false));
        findAndHookMethod("com.android.thememanager.basemodule.utils.r", "e", XC_MethodReplacement.returnConstant("dagu"));*/

    }
}
