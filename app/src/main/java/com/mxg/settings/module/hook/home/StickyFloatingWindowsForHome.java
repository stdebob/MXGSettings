// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class StickyFloatingWindowsForHome extends BaseHook {

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.recents.views.RecentsContainer", "onAttachedToWindow", new MethodHook() {
            @SuppressLint("UnspecifiedRegisterReceiverFlag")
            @Override
            protected void after(MethodHookParam param) {
                Context mContext = (Context) XposedHelpers.callMethod(param.thisObject, "getContext");
                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        try {
                            String pkgName = intent.getStringExtra("package");
                            if (pkgName != null) {
                                XposedHelpers.callMethod(param.thisObject, "dismissRecentsToLaunchTargetTaskOrHome", pkgName, true);
                            }
                        } catch (Throwable t) {
                            logW(TAG, StickyFloatingWindowsForHome.this.lpparam.packageName, t);
                        }
                    }
                }, new IntentFilter(ACTION_PREFIX + "dismissRecentsWhenFreeWindowOpen"));
            }
        });
    }
}
