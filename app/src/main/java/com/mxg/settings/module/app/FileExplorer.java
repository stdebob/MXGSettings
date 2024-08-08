// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.fileexplorer.SelectName;
import com.mxg.settings.module.hook.fileexplorer.UnlockFileParse;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.android.fileexplorer", isPad = false, tarAndroid = 33)
public class FileExplorer extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnlockFileParse(), mPrefsMap.getBoolean("file_explorer_unlock_file_parse"));
        initHook(SelectName.INSTANCE, mPrefsMap.getBoolean("file_explorer_can_selectable") || mPrefsMap.getBoolean("file_explorer_is_single_line"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
