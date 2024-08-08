// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import com.mxg.settings.module.base.BaseHook;


public class UnlockCvlens extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.android.camera.CameraSettings", "isSupportCvLensDevice", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(true);
            }
        });
        try {
            hookAllMethods("com.android.camera.CameraSettings", "getCvLensVersion", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(2);
                }
            });
            hookAllMethods("com.android.camera2.CameraCapabilities", "getCvLensVersion", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(2);
                }
            });
            hookAllMethods("com.android.camera2.CameraCapabilitiesUtil", "getCvLensVersion", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(2);
                }
            });
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "try to hook CvLensVersion failed" + e);
            throw new RuntimeException(e);
        }
    }
}
