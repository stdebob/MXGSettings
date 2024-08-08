// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.miwallpaper;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class UnlockSuperWallpaper extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.miui.superwallpaper.SuperWallpaperUtils", "initEnableSuperWallpaper", Context.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        XposedHelpers.setStaticBooleanField(findClassIfExists("com.miui.superwallpaper.SuperWallpaperUtils"), "sEnableSuperWallpaper", true);
    }
}
