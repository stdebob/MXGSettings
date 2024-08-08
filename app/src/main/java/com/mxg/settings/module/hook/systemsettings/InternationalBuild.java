// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class InternationalBuild extends BaseHook {
    @Override
    public void init() {
        XposedHelpers.setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD", true);
    }
}
