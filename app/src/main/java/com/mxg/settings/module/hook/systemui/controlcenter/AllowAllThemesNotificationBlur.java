// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class AllowAllThemesNotificationBlur extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.systemui.shade.MiuiNotificationPanelViewController$MiuiConfigurationListener", "onMiBlurChanged", boolean.class,new MethodHook(){
            XC_MethodHook.Unhook isDefaultLockScreenThemeHook;

            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(findClass("com.miui.utils.MiuiThemeUtils"), "sDefaultSysUiTheme", true);
                isDefaultLockScreenThemeHook = findAndHookMethodUseUnhook("com.miui.systemui.util.CommonUtil", lpparam.classLoader, "isDefaultLockScreenTheme", new MethodHook(){
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                });
            }

            @Override
            protected void after(MethodHookParam param) throws Throwable {
                if (isDefaultLockScreenThemeHook != null) isDefaultLockScreenThemeHook.unhook();
                isDefaultLockScreenThemeHook = null;
            }
        });
        findAndHookMethod("com.miui.systemui.util.MiBlurCompat","getBackgroundBlurOpened",Context.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.android.systemui.statusbar.notification.NotificationUtil", "isBackgroundBlurOpened",Context.class, new MethodHook(){
            XC_MethodHook.Unhook isDefaultLockScreenThemeHook;

            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(findClass("com.miui.utils.MiuiThemeUtils"), "sDefaultSysUiTheme", true);
                isDefaultLockScreenThemeHook = findAndHookMethodUseUnhook("com.miui.systemui.util.CommonUtil", lpparam.classLoader, "isDefaultLockScreenTheme", new MethodHook(){
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                });
            }

            @Override
            protected void after(MethodHookParam param) throws Throwable {
                if (isDefaultLockScreenThemeHook != null) isDefaultLockScreenThemeHook.unhook();
                isDefaultLockScreenThemeHook = null;
            }
        });
    }
}
