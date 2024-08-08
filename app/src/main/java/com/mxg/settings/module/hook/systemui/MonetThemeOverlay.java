// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import android.content.Context;
import android.os.Handler;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.prefs.PrefType;
import com.mxg.settings.utils.prefs.PrefsChangeObserver;


public class MonetThemeOverlay extends BaseHook {

    Class<?> THEME_CLASS_AOSP;
    Context mContext;
    Handler mHandler;

    @Override
    public void init() {
        THEME_CLASS_AOSP = findClassIfExists("com.android.systemui.theme.ThemeOverlayController");
        String mMethodName;
        if (isMoreAndroidVersion(34)) mMethodName = "createOverlays";
        else mMethodName = "getOverlay";

        hookAllConstructors(THEME_CLASS_AOSP, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                mContext = (Context) param.args[0];
                /*mHandler = (Handler) param.args[2];*/
            }
        });

        hookAllMethods(THEME_CLASS_AOSP, mMethodName, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                mHandler = new Handler(mContext.getMainLooper());
                new PrefsChangeObserver(mContext, mHandler, true, PrefType.Integer,
                        "prefs_key_system_ui_monet_overlay_custom_color", -1);
                int color = mPrefsMap.getInt("system_ui_monet_overlay_custom_color", -1);
                param.args[0] = color;
            }
        });
    }
}
