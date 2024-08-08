// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class HideCleanUp extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        hookAllMethods(
            findClassIfExists("com.miui.home.recents.views.RecentsContainer"),
            "onFinishInflate",
            new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    View mView = (View) XposedHelpers.getObjectField(param.thisObject, "mClearAnimView");
                    mView.setVisibility(View.GONE);
                }
            }
        );
    }
}
