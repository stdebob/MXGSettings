// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.view.MotionEvent;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.log.AndroidLogUtils;

import de.robv.android.xposed.XposedHelpers;

public class SwitchControlPanel extends BaseHook {

    Class<?> mControlPanelWindowManager;

    @Override
    public void init() {

        mControlPanelWindowManager = findClassIfExists("com.android.systemui.controlcenter.phone.ControlPanelWindowManager");

        findAndHookMethod(mControlPanelWindowManager, "dispatchToControlPanel", MotionEvent.class, float.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                float f = (float) param.args[1];
                XposedHelpers.setFloatField(param.thisObject, "mDownX", f);
                float mDownX = XposedHelpers.getFloatField(param.thisObject, "mDownX");
                int i = (Float.compare(mDownX, f / 2.0f));
                AndroidLogUtils.logI(TAG, "mDownX：" + mDownX + "in before");
                AndroidLogUtils.logI(TAG, "f：" + f + "in before");
                AndroidLogUtils.logI(TAG, "：" + i + "in before");
                i *= -1;
                int i2 = i;
                AndroidLogUtils.logI(TAG, "：" + i2 + "in before");
            }

            @Override
            protected void after(MethodHookParam param) {
                float mDownX = XposedHelpers.getFloatField(param.thisObject, "mDownX");
                float f = (float) param.args[1];
                int i = (Float.compare(mDownX, f / 2.0f));
                AndroidLogUtils.logI(TAG, "mDownX：" + mDownX + "in after");
                AndroidLogUtils.logI(TAG, "f：" + f + "in after");
                AndroidLogUtils.logI(TAG, "：" + i + "in after");
                i *= -1;
                int i2 = i;
                AndroidLogUtils.logI(TAG, "：" + i2 + "in after");
            }
        });
    }
}
