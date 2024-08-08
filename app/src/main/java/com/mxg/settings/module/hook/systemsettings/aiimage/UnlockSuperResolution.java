// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings.aiimage;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

public class UnlockSuperResolution extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "getSrForVideoStatus", Context.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "getSrForImageStatus", Context.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "getS2hStatus", Context.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "isSrForVideoSupport", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "isSrForImageSupport", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.settings.display.ScreenEnhanceEngineStatusCheck", "isS2hSupport", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
    }
}
