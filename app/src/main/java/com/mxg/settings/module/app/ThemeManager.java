// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.thememanager.AllowDownloadMore;
import com.mxg.settings.module.hook.thememanager.AllowThirdTheme;
import com.mxg.settings.module.hook.thememanager.DisableThemeAdNew;
import com.mxg.settings.module.hook.thememanager.EnableFoldTheme;
import com.mxg.settings.module.hook.thememanager.EnablePadTheme;
import com.mxg.settings.module.hook.thememanager.VersionCodeModify;

@HookExpand(pkg = "com.android.thememanager", isPad = false, tarAndroid = 33)
public class ThemeManager extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new AllowThirdTheme(), mPrefsMap.getBoolean("system_framework_allow_third_theme"));
        initHook(new DisableThemeAdNew(), mPrefsMap.getBoolean("various_theme_disable_ads"));
        initHook(new AllowDownloadMore(), mPrefsMap.getBoolean("theme_manager_allow_download_more"));
        initHook(new EnablePadTheme(), mPrefsMap.getBoolean("various_theme_enable_pad_theme"));
        initHook(new EnableFoldTheme(), mPrefsMap.getBoolean("various_theme_enable_fold_theme"));

        // 修改版本号
        initHook(new VersionCodeModify(), mPrefsMap.getStringAsInt("theme_manager_new_version_code_modify", 0) != 0);
    }

}
