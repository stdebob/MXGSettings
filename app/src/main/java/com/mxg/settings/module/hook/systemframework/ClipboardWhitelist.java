// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

import java.util.Set;

import de.robv.android.xposed.XposedHelpers;

public class ClipboardWhitelist extends BaseHook {
    @Override
    public void init() {
        Class<?> clipboardClass = XposedHelpers.findClass("com.android.server.clipboard.ClipboardService", lpparam.classLoader);
        String key = "system_framework_clipboard_whitelist_apps";
        Set<String> selectedApps = mPrefsMap.getStringSet(key);
        hookAllMethods(clipboardClass, "clipboardAccessAllowed", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                for (String pkgName : selectedApps) {
                    if (pkgName.equals(param.args[1])) {
                        param.setResult(true);
                        return;
                    }
                }
            }
        });
    }
}
