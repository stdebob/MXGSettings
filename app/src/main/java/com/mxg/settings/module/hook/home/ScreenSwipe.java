// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.hook.GlobalActions;
import com.mxg.settings.utils.log.AndroidLogUtils;
import com.mxg.settings.utils.prefs.PrefType;
import com.mxg.settings.utils.prefs.PrefsChangeObserver;
import com.mxg.settings.utils.prefs.PrefsUtils;

import de.robv.android.xposed.XposedHelpers;

public class ScreenSwipe extends BaseHook {

    Class<?> mLauncher;
    Class<?> mWorkspace;

    @Override
    public void init() {
        mLauncher = findClassIfExists("com.miui.home.launcher.Launcher");
        mWorkspace = findClassIfExists("com.miui.home.launcher.Workspace");

        findAndHookMethod(mWorkspace, "onVerticalGesture", int.class, MotionEvent.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                if ((boolean) XposedHelpers.callMethod(param.thisObject, "isInNormalEditingMode"))
                    return;
                String key = null;
                Context helperContext = ((ViewGroup) param.thisObject).getContext();
                int numOfFingers = 1;
                if (param.args[1] != null)
                    numOfFingers = ((MotionEvent) param.args[1]).getPointerCount();
                if ((int) param.args[0] == 11) {
                    if (numOfFingers == 1)
                        key = "prefs_key_home_gesture_down_swipe";
                    else if (numOfFingers == 2)
                        key = "prefs_key_home_gesture_down_swipe2";
                    if (GlobalActions.handleAction(helperContext, key)) param.setResult(true);
                } else if ((int) param.args[0] == 10) {
                    if (numOfFingers == 1)
                        key = "prefs_key_home_gesture_up_swipe";
                    else if (numOfFingers == 2)
                        key = "prefs_key_home_gesture_up_swipe2";
                    if (GlobalActions.handleAction(helperContext, key)) param.setResult(true);
                }
            }
        });

        findAndHookMethod(mLauncher, "onCreate", Bundle.class, new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) {
                final Activity act = (Activity) param.thisObject;
                Handler mHandler = (Handler) XposedHelpers.getObjectField(act, "mHandler");
                new PrefsChangeObserver(act, mHandler) {
                    @Override
                    public void onChange(PrefType type, Uri uri, String name, Object def) {
                        try {
                            if (name.contains("prefs_key_home_gesture_down_swipe"))
                                switch (type) {
                                    case PrefType.String ->
                                            mPrefsMap.put(name, PrefsUtils.getSharedStringPrefs(act, name, ""));
                                    case PrefType.Integer ->
                                            mPrefsMap.put(name, PrefsUtils.getSharedIntPrefs(act, name, 1));
                                    case PrefType.Boolean ->
                                            mPrefsMap.put(name, PrefsUtils.getSharedBoolPrefs(act, name, false));
                                }
                        } catch (Throwable t) {
                            AndroidLogUtils.logD(TAG, "setAction", t);
                        }
                    }
                };
            }
        });

        findAndHookMethodSilently("com.miui.home.launcher.uioverrides.StatusBarSwipeController", "canInterceptTouch", MotionEvent.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) {
                if (mPrefsMap.getInt("home_gesture_down_swipe_action", 0) > 0)
                    param.setResult(false);
            }
        });

        // content_center, global_search, notification_bar
        findAndHookMethodSilently("com.miui.home.launcher.allapps.LauncherMode", "getPullDownGesture", Context.class, new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) {
                if (PrefsUtils.getSharedIntPrefs((Context) param.args[0], "prefs_key_home_gesture_down_swipe_action", 1) > 1)
                    param.setResult("no_action");
            }
        });

        // content_center, global_search
        findAndHookMethodSilently("com.miui.home.launcher.allapps.LauncherMode", "getSlideUpGesture", Context.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) {
                if (PrefsUtils.getSharedIntPrefs((Context) param.args[0], "prefs_key_home_gesture_up_swipe_action", 0) > 0)
                    param.setResult("no_action");
            }
        });

        if (findAndHookMethodSilently("com.miui.home.launcher.DeviceConfig", "isGlobalSearchEnable", Context.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) {
                if (PrefsUtils.getSharedIntPrefs((Context) param.args[0], "prefs_key_home_gesture_up_swipe_action", 0) > 0)
                    param.setResult(false);
            }
        })) {
            findAndHookMethodSilently("com.miui.home.launcher.search.SearchEdgeLayout", "isTopSearchEnable", new MethodHook() {
                @Override
                protected void before(final MethodHookParam param) {
                    View view = (View) param.thisObject;
                    if (PrefsUtils.getSharedIntPrefs(view.getContext(), "prefs_key_home_gesture_down_swipe_action", 0) > 0)
                        param.setResult(false);
                }
            });

            findAndHookMethodSilently("com.miui.home.launcher.search.SearchEdgeLayout", "isBottomGlobalSearchEnable", new MethodHook() {
                @Override
                protected void before(final MethodHookParam param) {
                    View view = (View) param.thisObject;
                    if (PrefsUtils.getSharedIntPrefs(view.getContext(), "prefs_key_home_gesture_up_swipe_action", 0) > 0)
                        param.setResult(false);
                }
            });

            findAndHookMethodSilently("com.miui.home.launcher.DeviceConfig", "isGlobalSearchBottomEffectEnable", Context.class, new MethodHook() {
                @Override
                protected void before(final MethodHookParam param) {
                    if (PrefsUtils.getSharedIntPrefs((Context) param.args[0], "prefs_key_home_gesture_up_swipe_action", 0) > 0)
                        param.setResult(false);
                }
            });
        } else if (!findAndHookMethodSilently("com.miui.home.launcher.DeviceConfig", "allowedSlidingUpToStartGolbalSearch", Context.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) {
                if (PrefsUtils.getSharedIntPrefs((Context) param.args[0], "prefs_key_home_gesture_up_swipe_action", 0) > 0)
                    param.setResult(false);
            }
        })) if (lpparam.packageName.equals("com.miui.home"))
            logI(TAG, this.lpparam.packageName, "Cannot disable swipe up search");
    }
}
