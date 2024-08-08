// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.aod;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class UnlockAodAon extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Class<?> mAodUtils = findClassIfExists("com.miui.aod.Utils");
        XposedHelpers.setStaticBooleanField(mAodUtils, "SUPPORT_AOD_AON", true);
    }
}
