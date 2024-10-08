// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import android.content.Context;
import android.os.PowerManager;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class MuteVisibleNotifications extends BaseHook {
    String NotificationLoadClass;

    @Override
    public void init() {
        if (isMoreAndroidVersion(34)) {
            NotificationLoadClass = "com.android.systemui.statusbar.notification.policy.MiuiAlertManager";
        } else {
            NotificationLoadClass = "com.android.systemui.statusbar.notification.policy.NotificationAlertController";
        }
        hookAllMethods(NotificationLoadClass, lpparam.classLoader, "buzzBeepBlink", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    Context mContext = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
                    PowerManager powerMgr = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                    if (powerMgr.isInteractive()) {
                        param.setResult(null);
                    }
                }
            }
        );
    }
}
