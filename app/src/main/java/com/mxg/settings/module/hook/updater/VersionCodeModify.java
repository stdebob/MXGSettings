// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater;

import android.os.Build;
import android.text.TextUtils;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class VersionCodeModify extends BaseHook {

    Class<?> mApplication;

    @Override
    public void init() {

        mApplication = findClassIfExists("com.android.updater.Application");

        findAndHookMethod(mApplication, "onCreate", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                String mVersionCode = mPrefsMap.getString("various_updater_miui_version", "V14.0.22.11.26.DEV");
                if (!TextUtils.isEmpty(mVersionCode)) {
                    XposedHelpers.setStaticObjectField(Build.VERSION.class, "INCREMENTAL", mVersionCode);
                }
            }
        });
    }
}
