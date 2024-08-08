// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.layout;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class ShowClock extends BaseHook {

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.Workspace", "isScreenHasClockGadget", long.class, XC_MethodReplacement.returnConstant(false));
    }
}
