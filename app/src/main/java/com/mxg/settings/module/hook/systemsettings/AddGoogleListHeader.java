// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;

import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class AddGoogleListHeader extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Class<?> mMiuiSettings = findClassIfExists("com.android.settings.MiuiSettings");
        findAndHookMethod(mMiuiSettings, "updateHeaderList", List.class, new MethodHook(){
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                List<?> list = (List<?>) param.args[0];
                XposedHelpers.callMethod(param.thisObject, "AddGoogleSettingsHeaders", list);
            }
        });
    }
}
