// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.various;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class NoBrightness extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("android.view.Window",
            "setAttributes",
            "android.view.WindowManager$LayoutParams",
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    // logE(TAG, "setAttributes: " + param.args[0]);
                    Object layoutParams = param.args[0];
                    XposedHelpers.setObjectField(layoutParams, "screenBrightness", -1.0f);
                    // param.setResult(null);
                }
            }
        );

        findAndHookMethod("android.view.WindowManager$LayoutParams",
            "copyFrom",
            "android.view.WindowManager$LayoutParams",
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    // logE(TAG, "copyFrom: " + param.args[0]);
                    Object layoutParams = param.args[0];
                    XposedHelpers.setObjectField(layoutParams, "screenBrightness", -1.0f);
                }
            }
        );

    }
}
