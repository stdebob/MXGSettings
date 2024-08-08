// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.gallery;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;

public class ChangeBackupServer extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        boolean isXiaomi = mPrefsMap.getStringAsInt("gallery_backup_server", 0) == 1;
        boolean isGoogle = mPrefsMap.getStringAsInt("gallery_backup_server", 0) == 2;
        boolean isOneDrive = mPrefsMap.getStringAsInt("gallery_backup_server", 0) == 3;
        if (isOneDrive) {
            findAndHookMethod("com.miui.gallery.ui.GallerySettingsFragment", "initGlobalBackupPreference", new MethodHook() {
                XC_MethodHook.Unhook isInternationalHook;

                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    isInternationalHook = findAndHookMethodUseUnhook("com.miui.gallery.util.BaseBuildUtil", lpparam.classLoader, "isInternational", XC_MethodReplacement.returnConstant(true));
                }

                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    if (isInternationalHook != null) isInternationalHook.unhook();
                    isInternationalHook = null;
                }
            });
            findAndHookMethod("com.miui.gallery.util.PhotoModelTypeUtil", "isSupportOneDrive", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } else {
            if (isXiaomi) {
                findAndHookMethod("com.miui.gallery.ui.GallerySettingsFragment", "initGlobalBackupPreference", new MethodHook() {
                    XC_MethodHook.Unhook isInternationalHook;

                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        isInternationalHook = findAndHookMethodUseUnhook("com.miui.gallery.util.BaseBuildUtil", lpparam.classLoader, "isInternational", XC_MethodReplacement.returnConstant(false));
                    }

                    @Override
                    protected void after(MethodHookParam param) throws Throwable {
                        if (isInternationalHook != null) isInternationalHook.unhook();
                        isInternationalHook = null;
                    }
                });
            }
            try {
                findAndHookMethod("com.miui.gallery.transfer.GoogleSyncHelper", "isCloudServiceOffLine", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult(isGoogle);
                    }
                });
            } catch (Throwable t) {
                findAndHookMethod("com.miui.gallery.util.BuildUtil", "isGlobal", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult(isGoogle);
                    }
                });
            }
        }
    }
}
