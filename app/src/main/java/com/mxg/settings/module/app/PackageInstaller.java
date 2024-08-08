// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import android.text.TextUtils;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.packageinstaller.AllAsSystemApp;
import com.mxg.settings.module.hook.packageinstaller.DisableAd;
import com.mxg.settings.module.hook.packageinstaller.DisableAppInfoUpload;
import com.mxg.settings.module.hook.packageinstaller.DisableCountChecking;
import com.mxg.settings.module.hook.packageinstaller.DisableInstallerFullSafeVersion;
import com.mxg.settings.module.hook.packageinstaller.DisableSafeModelTip;
import com.mxg.settings.module.hook.packageinstaller.DisplayMoreApkInfoNew;
import com.mxg.settings.module.hook.packageinstaller.InstallRiskDisable;
import com.mxg.settings.module.hook.packageinstaller.InstallSource;

@HookExpand(pkg = "com.miui.packageinstaller", isPad = false, tarAndroid = 33)
public class PackageInstaller extends BaseModule {

    public void handleLoadPackage() {

        //
        /*initHook(new MiuiPackageInstallModify(), mPrefsMap.getBoolean("miui_package_installer_modify"));*/

        // 禁用广告
        initHook(new DisableAd(), mPrefsMap.getBoolean("miui_package_installer_disable_ad"));

        // 禁用风险检测
        initHook(InstallRiskDisable.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_install_risk"));

        // 禁用安全守护提示
        initHook(DisableSafeModelTip.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_safe_model_tip"));

        // 允许更新系统应用
        initHook(AllAsSystemApp.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_update_system_app"));

        // 自定义安装来源
        initHook(new InstallSource(), !TextUtils.isEmpty(mPrefsMap.getString("miui_package_installer_install_source", "com.android.fileexplorer")));

        // 显示更多安装包信息
        // initHook(new DisplayMoreApkInfo(), mPrefsMap.getBoolean("miui_package_installer_apk_info"));
        initHook(DisplayMoreApkInfoNew.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_apk_info"));
        initHook(new DisableInstallerFullSafeVersion(), mPrefsMap.getBoolean("miui_package_installer_apk_info"));

        // 禁用频繁安装应用检查
        initHook(DisableCountChecking.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_count_checking"));

        // 禁用安装前后上传应用信息, 开启后会无法扫描病毒
        initHook(DisableAppInfoUpload.INSTANCE, mPrefsMap.getBoolean("miui_package_installer_upload_appinfo"));

    }
}
