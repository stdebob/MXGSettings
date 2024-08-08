// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.various.CollapseMiuiTitle;
import com.mxg.settings.module.hook.various.DialogCustom;
import com.mxg.settings.module.hook.various.MiuiAppNoOverScroll;

import java.util.Arrays;
import java.util.HashSet;

@HookExpand(pkg = "VariousSystemApps", isPad = false, tarAndroid = 33, skip = true)
public class VariousSystemApps extends BaseModule {
    Class<?> mHelpers;
    String mPackageName;
    boolean isMiuiApps;

    @Override
    public void handleLoadPackage() {
        mPackageName = mLoadPackageParam.packageName;
        isMiuiApps = mPackageName.startsWith("com.miui") || mPackageName.startsWith("com.xiaomi") || miuiDialogCustomApps.contains(mPackageName);

        initHook(new MiuiAppNoOverScroll(), isMiuiOverScrollApps());
        initHook(new DialogCustom(), isMiuiDialogCustom());

        initHook(new CollapseMiuiTitle(), isCollapseMiuiTitleApps());

        // initHook(new NoBrightness(), isPay(mPackageName));
    }

    private boolean isPay(String param) {
        return mPrefsMap.getBoolean("various_nobrightness") && checkPay(param);
    }

    private boolean checkPay(String packageParam) {
        switch (packageParam) {
            case "com.tencent.mobileqq", "com.tencent.mm",
                    "com.eg.android.AlipayGphone" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean isMiuiOverScrollApps() {
        return mPrefsMap.getBoolean("various_no_overscroll") && miuiOverScrollApps.contains(mPackageName);
    }

    private boolean isMiuiDialogCustom() {
        return mPrefsMap.getStringAsInt("various_dialog_gravity", 0) != 0 && isMiuiApps;
    }

    private boolean isCollapseMiuiTitleApps() {
        return mPrefsMap.getStringAsInt("various_collapse_miui_title", 0) != 0 && collapseMiuiTitleApps.contains(mPackageName);
    }

    HashSet<String> miuiOverScrollApps = new HashSet<>(Arrays.asList(
            "com.android.fileexplorer",
            "com.android.providers.downloads.ui",
            "com.android.settings"
    ));

    HashSet<String> miuiDialogCustomApps = new HashSet<>(Arrays.asList(
            "com.android.fileexplorer",
            "com.android.providers.downloads.ui",
            "com.android.settings"
    ));

    HashSet<String> collapseMiuiTitleApps = new HashSet<>(Arrays.asList(
            "com.android.fileexplorer",
            "com.android.providers.downloads.ui",
            "com.android.settings"
    ));


}
