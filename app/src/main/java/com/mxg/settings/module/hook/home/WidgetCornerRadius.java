// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.devicesdk.DisplayUtils;

public class WidgetCornerRadius extends BaseHook {

    Context mContext;

    @Override
    public void init() {

        hookAllConstructors("com.miui.home.launcher.maml.MaMlHostView", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                mContext = (Context) param.args[0];
            }
        });

        hookAllMethods("com.miui.home.launcher.maml.MaMlHostView", "computeRoundedCornerRadius", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult((float) DisplayUtils.dp2px(mPrefsMap.getInt("home_widget_corner_radius", 0)));
            }
        });


        hookAllConstructors("com.miui.home.launcher.LauncherAppWidgetHostView", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                mContext = (Context) param.args[0];
            }
        });

        hookAllMethods("com.miui.home.launcher.LauncherAppWidgetHostView", "computeRoundedCornerRadius", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult((float) DisplayUtils.dp2px(mPrefsMap.getInt("home_widget_corner_radius", 0)));
            }
        });
    }
}
