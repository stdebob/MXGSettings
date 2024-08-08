// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.externalstorage.DisableFolderCantUse;

@HookExpand(pkg = "com.android.externalstorage", isPad = false, tarAndroid = 33)
public class ExternalStorage extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DisableFolderCantUse(), mPrefsMap.getBoolean("various_disable_folder_cantuse"));
    }
}
