// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class AutoCollapse extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.systemui.qs.tileimpl.QSTileImpl", lpparam.classLoader, "click", View.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                Object mState = XposedHelpers.callMethod(param.thisObject, "getState");
                int state = XposedHelpers.getIntField(mState, "state");
                if (state != 0) {
                    String tileSpec = (String) XposedHelpers.callMethod(param.thisObject, "getTileSpec");
                    if (!"edit".equals(tileSpec)) {
                        Object mHost = XposedHelpers.getObjectField(param.thisObject, "mHost");
                        XposedHelpers.callMethod(mHost, "collapsePanels");
                    }
                }
            }
        });
    }
}
