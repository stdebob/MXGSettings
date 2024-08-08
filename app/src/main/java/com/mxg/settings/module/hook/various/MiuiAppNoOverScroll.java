// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.various;

import android.view.View;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class MiuiAppNoOverScroll extends BaseHook {


    @Override
    public void init() {

        Class<?> mSpringBackCls = findClassIfExists("miuix.springback.view.SpringBackLayout");
        Class<?> mRemixRvCls = findClassIfExists("androidx.recyclerview.widget.RemixRecyclerView");

        try {
            MethodHook hookParam = new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    XposedHelpers.setBooleanField(param.thisObject, "mSpringBackEnable", false);
                    param.args[0] = false;
                }
            };

            if (mSpringBackCls != null) {

                hookAllConstructors(mSpringBackCls, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        XposedHelpers.setBooleanField(param.thisObject, "mSpringBackEnable", false);
                    }
                });

                findAndHookMethodSilently(mSpringBackCls, "setSpringBackEnable", boolean.class, hookParam);
            }


            if (mRemixRvCls != null) {
                hookAllConstructors(mRemixRvCls, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        ((View) param.thisObject).setOverScrollMode(View.OVER_SCROLL_NEVER);
                        XposedHelpers.setBooleanField(param.thisObject, "mSpringBackEnable", false);
                    }
                });
                findAndHookMethodSilently(mRemixRvCls, "setSpringEnabled", boolean.class, hookParam);
            }
        } catch (Exception e) {
            logE(TAG, "TAG" + lpparam.packageName, e);
        }
    }
}
