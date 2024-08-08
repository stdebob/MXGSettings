// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

public class UserPresentAnimation extends BaseHook {

    Class<?> mUserPresentAnimationCompatV12Phone;

    @Override
    public void init() {
        mUserPresentAnimationCompatV12Phone = !isPad() ?
            findClassIfExists("com.miui.home.launcher.compat.UserPresentAnimationCompatV12Phone") :
        findClassIfExists("com.miui.home.launcher.compat.UserPresentAnimationCompatV12Spring");
        findAndHookMethod(mUserPresentAnimationCompatV12Phone, "getSpringAnimator", View.class, int.class, float.class, float.class, float.class, float.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.args[4] = 0.5f;
                param.args[5] = 0.5f;
            }
        });
    }
}
