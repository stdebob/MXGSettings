// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.contentextension;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;


public class UseThirdPartyBrowser extends BaseHook {

    @Override
    public void init() {
        // XposedBridge.log("Hook到传送门进程！");
        final Class<?> clazz = XposedHelpers.findClass("com.miui.contentextension.utils.AppsUtils", lpparam.classLoader);
        // getClassInfo(clazz);

        XposedHelpers.findAndHookMethod(clazz, "getIntentWithBrowser", String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                logI(TAG, UseThirdPartyBrowser.this.lpparam.packageName, "hooked url " + param.args[0].toString());
                Uri uri = Uri.parse(param.args[0].toString());
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                return intent;
            }
        });

        XposedHelpers.findAndHookMethod(clazz, "openGlobalSearch", Context.class, String.class, String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                logI(TAG, UseThirdPartyBrowser.this.lpparam.packageName, "hooked all-search on, word is " + param.args[1].toString() + ", from " + param.args[2].toString());
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, param.args[1].toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Context) param.args[0]).startActivity(intent);
                } catch (Exception e) {
                    logE(TAG, UseThirdPartyBrowser.this.lpparam.packageName, e);
                }
                return null;
            }
        });
    }
}
