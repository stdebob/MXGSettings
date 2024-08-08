// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.thememanager;

import com.mxg.settings.module.base.BaseHook;

public class VersionCodeModify extends BaseHook {

    Class<?> mThemeApplication;
    Integer mChargeVersionCode;

    @Override
    public void init() {
        mThemeApplication = findClassIfExists("com.android.thememanager.ThemeApplication");
        mChargeVersionCode = mPrefsMap.getStringAsInt("theme_manager_new_version_code_modify", 0);

        findAndHookMethod(mThemeApplication, "onCreate", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {

                findAndHookMethod("android.os.SystemProperties", "get", String.class, String.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        // 待定，等找个时间完善
                        if ("ro.miui.ui.version.code".equals(param.args[0])) {
                            switch (mChargeVersionCode) {
                                case 110 -> param.setResult("110");
                                case 120 -> param.setResult("120");
                                case 125 -> param.setResult("125");
                                case 130 -> param.setResult("130");
                                case 140 -> param.setResult("140");
                                case 150 -> param.setResult("816");
                            }
                        }
                    }
                });
            }
        });
    }
}
