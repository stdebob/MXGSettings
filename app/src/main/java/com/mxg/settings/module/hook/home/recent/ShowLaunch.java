// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent;

import com.mxg.settings.module.base.BaseHook;

public class ShowLaunch extends BaseHook {
    //from XiaomiHelper by HowieHChen
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.home.recents.NavStubView", "changeAlphaScaleForFsGesture", float.class, float.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.args[0] = (1.0f - (float) mPrefsMap.getInt("home_recent_show_launch_alpha", 100) / 100) * (float) param.args[0] + (float) mPrefsMap.getInt("home_recent_show_launch_alpha", 100) / 100;
            }
        });
        findAndHookMethod("com.miui.home.recents.OverviewState", "getShortcutMenuLayerAlpha", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult((1.0f - (float) mPrefsMap.getInt("home_recent_show_launch_alpha", 100) / 100) * (float) param.getResult() + (float) mPrefsMap.getInt("home_recent_show_launch_alpha", 100) / 100);
            }
        });
        findAndHookMethod("com.miui.home.recents.OverviewState", "getShortcutMenuLayerScale", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult((float) mPrefsMap.getInt("home_recent_show_launch_size", 95) / 100);
            }
        });
    }
}
