// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.gesture;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;

import com.mxg.settings.module.hook.GlobalActions;
import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class CornerSlide extends BaseHook {
    public int inDirection = 0;

    Context mContext;

    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.systemui.shared.recents.system.AssistManager",
            "isSupportGoogleAssist", int.class,
            MethodHook.returnConstant(true));
        Class<?> FsGestureAssistHelper = findClassIfExists("com.miui.home.recents.FsGestureAssistHelper");
        findAndHookMethod(FsGestureAssistHelper, "canTriggerAssistantAction",
            float.class, float.class, int.class,
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    boolean isDisabled = (boolean) XposedHelpers.callStaticMethod(FsGestureAssistHelper,
                        "isAssistantGestureDisabled", param.args[2]);
                    if (!isDisabled) {
                        int mAssistantWidth = (int) XposedHelpers.getObjectField(param.thisObject, "mAssistantWidth");
                        float f = (float) param.args[0];
                        float f2 = (float) param.args[1];
                        if (f < mAssistantWidth || f > f2 - mAssistantWidth) {
                            param.setResult(true);
                            return;
                        }
                    }
                    param.setResult(false);
                }
            }
        );

        // final int[] inDirection = {0};
        hookAllMethods(FsGestureAssistHelper,
            "handleTouchEvent", new MethodHook() {
                @Override
                protected void after(MethodHookParam param) {
                    MotionEvent motionEvent = (MotionEvent) param.args[0];
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        float mDownX = XposedHelpers.getFloatField(param.thisObject, "mDownX");
                        int mAssistantWidth = XposedHelpers.getIntField(param.thisObject, "mAssistantWidth");
                        inDirection = mDownX < mAssistantWidth ? 0 : 1;
                    }
                }
            }
        );

        findAndHookConstructor("com.miui.home.recents.NavStubView",
            Context.class, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) {
                    mContext = (Context) param.args[0];
                }
            }
        );

        findAndHookMethod("com.miui.home.recents.SystemUiProxyWrapper",
            "startAssistant", Bundle.class,
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    Bundle bundle = (Bundle) param.args[0];
                    if (bundle.getInt("triggered_by", 0) != 83 || bundle.getInt("invocation_type", 0) != 1) {
                        return;
                    }
                    String direction = inDirection == 1 ? "right" : "left";
                    GlobalActions.handleAction(
                        AndroidAppHelper.currentApplication(),
                        "prefs_key_home_navigation_assist_" + direction + "_slide"
                    );
                    /*logE(TAG, "bundle: " + bundle+" rig: "+direction);
                    if (mPrefsMap.getInt("home_navigation_assist_" + direction + "_slide_action", 0) == 10) {

                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) XposedHelpers.callMethod(
                            XposedHelpers.callStaticMethod(findClassIfExists("com.miui.home.recents.RecentsModel"), "getInstance",
                                mContext), "getRunningTaskContainHome");
                        LockApp.needLockApp(mContext, runningTaskInfo, lpparam);
                    } else {

                    }*/
                }
            }
        );
    }
}
