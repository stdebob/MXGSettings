// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.lbe.AutoStart;
import com.mxg.settings.module.hook.lbe.DisableClipboardTip;

@HookExpand(pkg = "com.lbe.security.miui", isPad = false, tarAndroid = 33)
public class Lbe extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new AutoStart(), mPrefsMap.getBoolean("lbe_auto_start"));
        initHook(DisableClipboardTip.INSTANCE, mPrefsMap.getBoolean("lbe_clipboard_tip_toast"));
    }
}
