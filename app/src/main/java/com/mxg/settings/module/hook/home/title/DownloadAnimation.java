// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import com.mxg.settings.module.base.BaseHook;

public class DownloadAnimation extends BaseHook {
    @Override
    public void init() {
        try{
            hookAllMethods("com.miui.home.launcher.common.DeviceLevelUtils", "needMamlProgressIcon", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
            hookAllMethods("com.miui.home.launcher.common.DeviceLevelUtils", "needRemoveDownloadAnimationDevice", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
        } catch (Exception e) {
            hookAllMethods("com.miui.home.launcher.common.CpuLevelUtils", "needMamlDownload", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        }
    }
}
