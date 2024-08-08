// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isAndroidVersion;

import com.mxg.settings.module.base.BaseHook;
import de.robv.android.xposed.XposedHelpers;

public class NotificationFreeform extends BaseHook {
    @Override
    public void init() {
        if (isAndroidVersion(34)) {
            findAndHookMethod(findClassIfExists("com.android.systemui.statusbar.notification.row.MiuiExpandableNotificationRow"), "updateMiniWindowBar", new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    super.after(param);
                    XposedHelpers.setObjectField(param.thisObject, "mCanSlide", true);
                }
            });
        } else {
            findAndHookMethod(findClassIfExists("com.android.systemui.statusbar.notification.NotificationSettingsManager"), "canSlide", String.class, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        }
    }
}
