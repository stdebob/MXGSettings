// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.backup.AllowBackupAllApps;
import com.mxg.settings.module.hook.backup.UnlockBrokenScreenBackup;

@HookExpand(pkg = "com.miui.backup", isPad = false, tarAndroid = 33)
public class Backup extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnlockBrokenScreenBackup(), mPrefsMap.getBoolean("backup_unlock_broken_screen_backup"));
        initHook(new AllowBackupAllApps(), mPrefsMap.getBoolean("backup_allow_all_apps"));
    }
}
