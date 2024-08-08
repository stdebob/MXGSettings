// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

public class AllowShareApk extends BaseHook{
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.home.launcher.common.Utilities", "isSecurityCenterSupportShareAPK", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(false);
                }
            }
        );
        findAndHookMethod("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem$ShareAppShortcutMenuItem", "isValid", "com.miui.home.launcher.ItemInfo", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    findAndHookMethod("com.miui.home.launcher.common.Utilities", "isSystemPackage", Context.class, String.class, new MethodHook() {
                            @Override
                            protected void before(MethodHookParam param) {
                                param.setResult(false);
                            }
                        }
                    );
                }
            }
        );
        //mResHook.setResReplacement("com.miui.home", "XML", "file_paths", R.xml.hook_home_file_paths);

    }
}
