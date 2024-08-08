// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.incallui;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class AnswerInHeadUp extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.incallui.InCallPresenter", "answerIncomingCall", Context.class, String.class, int.class, boolean.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                boolean showUi = (boolean) param.args[3];
                if (showUi) {
                    Object foregroundInfo = XposedHelpers.callStaticMethod(findClassIfExists("miui.process.ProcessManager"),
                            "getForegroundInfo");
                    if (foregroundInfo != null) {
                        String topPackage = (String) XposedHelpers.getObjectField(foregroundInfo, "mForegroundPackageName");
                        /*if (!"com.miui.home".equals(topPackage)) {
                            param.args[3] = false;
                        }*/
                    }
                }
            }
        });
    }
}
