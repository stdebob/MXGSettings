// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.creation.UnlockCreation;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.miui.creation", isPad = false, tarAndroid = 33)
public class Creation extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(UnlockCreation.INSTANCE, mPrefsMap.getBoolean("creation_unlock_enable"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
