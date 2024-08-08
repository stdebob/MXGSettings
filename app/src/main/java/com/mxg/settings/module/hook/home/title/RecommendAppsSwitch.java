// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class RecommendAppsSwitch extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.home.launcher.Folder",
            "showRecommendAppsSwitch", boolean.class, boolean.class,
            new MethodHook() {
                @Override
                protected void after(MethodHookParam param) {
                    View mRecommendAppsSwitch = (View) XposedHelpers.getObjectField(param.thisObject,
                        "mRecommendAppsSwitch");
                    mRecommendAppsSwitch.setVisibility(View.GONE);
                }
            }
        );
    }
}
