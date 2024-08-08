// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.various;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class CollapseMiuiTitle extends BaseHook {

    @Override
    public void init() {
        Class<?> abvCls = findClassIfExists("com.miui.internal.widget.AbsActionBarView");

        int opt = mPrefsMap.getStringAsInt("various_collapse_miui_title", 0);

        if (abvCls != null)
            hookAllConstructors(abvCls, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    XposedHelpers.setIntField(param.thisObject, "mExpandState", (int) XposedHelpers.getStaticObjectField(
                            findClassIfExists("miui.app.ActionBar"),
                            "STATE_EXPAND"));
                    XposedHelpers.setIntField(param.thisObject, "mInnerExpandState", (int) XposedHelpers.getStaticObjectField(
                            findClassIfExists("miui.app.ActionBar"),
                            "STATE_COLLAPSE"));
                    if (opt == 2)
                        XposedHelpers.setBooleanField(param.thisObject, "mResizable", false);
                }
            });

        abvCls = findClassIfExists("miuix.appcompat.internal.app.widget.ActionBarView");
        if (abvCls != null)
            hookAllConstructors(abvCls, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    try {
                        setExpandState(param.thisObject, opt == 1 || opt == 3);
                        setResizable(param.thisObject, opt == 3 || opt == 4);
                    } catch (Throwable ignore) {
                    }
                }
            });
    }

    private void setExpandState(Object obj, boolean state) {
        if (state) {
            XposedHelpers.callMethod(obj, "setExpandState", XposedHelpers.getObjectField(
                    findClassIfExists("miui.app.ActionBar"),
                    "STATE_COLLAPSE"));
        } else {
            XposedHelpers.callMethod(obj, "setExpandState", XposedHelpers.getObjectField(
                    findClassIfExists("miui.app.ActionBar"),
                    "STATE_EXPAND"));
        }
    }

    private void setResizable(Object obj, boolean state) {
        if (state) {
            XposedHelpers.callMethod(obj, "setResizable", false);
        } else {
            XposedHelpers.callMethod(obj, "setResizable", true);
        }
    }
}
