// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.content.pm.ApplicationInfo;
import android.graphics.Paint;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class QSDetailBackGround extends BaseHook {

    private ClassLoader mPluginLoader = null;

    @Override
    public void init() {
        findAndHookMethod("com.android.systemui.shared.plugins.PluginManagerImpl", "getClassLoader", ApplicationInfo.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                ApplicationInfo appInfo = (ApplicationInfo) param.args[0];
                if ("miui.systemui.plugin".equals(appInfo.packageName)) {
                    if (mPluginLoader == null) {
                        mPluginLoader = (ClassLoader) param.getResult();
                    }

                    hookAllMethods("miui.systemui.widget.SmoothRoundDrawable", mPluginLoader, "inflate", new MethodHook() {
                        @Override
                        protected void after(MethodHookParam param) throws Throwable {
                            Paint mPaint = (Paint) XposedHelpers.getObjectField(param.thisObject, "mPaint");
                            mPaint.setAlpha(mPrefsMap.getInt("system_control_center_qs_detail_bg", 0));
                        }
                    });
                }
            }
        });
    }
}
