// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.browser.DebugMode;
import com.mxg.settings.module.hook.browser.DisableReadFiles;
import com.mxg.settings.module.hook.browser.EnableDebugEnvironment;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.android.browser", isPad = false, tarAndroid = 33)
public class Browser extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DebugMode(), mPrefsMap.getBoolean("browser_debug_mode"));
        initHook(new DisableReadFiles(), mPrefsMap.getBoolean("browser_disable_blacklist"));
        initHook(new EnableDebugEnvironment(), mPrefsMap.getBoolean("browser_enable_debug_environment"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
