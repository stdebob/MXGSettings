// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import java.util.Objects;

public class LanguageMenuShowAllApps extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("android.util.FeatureFlagUtils", "isEnabled", Context.class, String.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                String param1 = (String) param.args[1];
                if (Objects.equals(param1, "settings_app_locale_opt_in_enabled")) param.setResult(false);
            }
        });

        findAndHookMethod("com.android.settings.applications.AppLocaleUtil", "isAppLocaleSupported", Context.class, String.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
    }
}
