// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.incallui;

import com.mxg.settings.module.base.BaseHook;


public class HideCrbt extends BaseHook {
    Class<?> loadClass;

    public void init() {
        loadClass = findClassIfExists("com.android.incallui.Call");
        try {
            hookAllMethods(loadClass, "getVideoCall", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
            findAndHookMethod(loadClass, "setPlayingVideoCrbt", int.class, boolean.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.args[0] = 0;
                    param.args[1] = Boolean.FALSE;
                }
            });
            /*hookAllMethods(loadClass, "setPlayingVideoCrbt", new MethodHook(){
                    Integer.TYPE, Boolean.TYPE, beforeHookedMethod()
            });*/
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "method hooked failed! " + e);
        }
    }
    /*public final void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
        HideCrbt.super.before(methodHookParam);
        methodHookParam.args[0]=0;
        methodHookParam.args[1]=Boolean.FALSE;
    }*/
}
