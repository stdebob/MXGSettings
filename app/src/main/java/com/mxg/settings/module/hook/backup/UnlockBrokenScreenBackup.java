// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.backup;

import android.os.Bundle;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class UnlockBrokenScreenBackup extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.backup.settings.MoreSettingsFragment", "onCreatePreferences", Bundle.class, String.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(findClassIfExists("com.miui.backup.settings.MoreSettingsFragment"), "IS_INTERNATIONAL", false);
            }
        });
    }
}
