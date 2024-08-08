// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.miwallpaper.UnlockSuperWallpaper;

@HookExpand(pkg = "com.miui.miwallpaper", isPad = false, tarAndroid = 33)
public class MiWallpaper extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnlockSuperWallpaper(), mPrefsMap.getBoolean("miwallpaper_unlock_super_wallpaper"));
    }
}
