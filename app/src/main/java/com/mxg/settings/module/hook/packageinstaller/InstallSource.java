// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.packageinstaller;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class InstallSource extends BaseHook {

    String mInstallSourcePackageName;

    @Override
    public void init() {

        mInstallSourcePackageName = mPrefsMap.getString("miui_package_installer_install_source", "com.android.fileexplorer");

        findAndHookMethodSilently("com.miui.packageInstaller.InstallStart",
            "getCallingPackage",
            XC_MethodReplacement.returnConstant(mInstallSourcePackageName));
    }
}
