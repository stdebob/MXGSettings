// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.navigation;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class BackGestureAreaWidth extends BaseHook {
    @Override
    public void init() {
        findAndHookMethodSilently("com.miui.home.recents.GestureStubView", "initScreenSizeAndDensity", int.class, new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) throws Throwable {
                int pct = mPrefsMap.getInt("home_navigation_back_area_width", 100);
                if (pct == 100) return;
                int mGestureStubDefaultSize = XposedHelpers.getIntField(param.thisObject, "mGestureStubDefaultSize");
                int mGestureStubSize  = XposedHelpers.getIntField(param.thisObject, "mGestureStubSize");
                mGestureStubDefaultSize = Math.round(mGestureStubDefaultSize * pct / 100f);
                mGestureStubSize = Math.round(mGestureStubSize * pct / 100f);
                XposedHelpers.setIntField(param.thisObject, "mGestureStubDefaultSize", mGestureStubDefaultSize);
                XposedHelpers.setIntField(param.thisObject, "mGestureStubSize", mGestureStubSize);
            }
        });

        findAndHookMethodSilently("com.miui.home.recents.GestureStubView", "setSize", int.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) throws Throwable {
                int pct = mPrefsMap.getInt("home_navigation_back_area_width", 100);
                if (pct == 100) return;
                int mGestureStubDefaultSize = XposedHelpers.getIntField(param.thisObject, "mGestureStubDefaultSize");
                if ((int)param.args[0] == mGestureStubDefaultSize) return;
                param.args[0] = Math.round((int)param.args[0] * pct / 100f);
            }
        });
    }
}
