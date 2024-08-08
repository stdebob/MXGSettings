// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import com.mxg.settings.module.base.BaseHook;

public class DisableTransparent extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        // from https://www.coolapk.com/feed/52893204?shareKey=YTA3MTRkZGJmYTJmNjVlNmI4MTY~&shareUid=1499664&shareFrom=com.coolapk.app_5.3
        String methodName;
        if (isMoreHyperOSVersion(1f)) methodName = "isTransparent";
        else methodName = "isTransparentMode";
        findAndHookMethod("com.android.systemui.statusbar.notification.row.NotificationContentInflaterInjector", methodName, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
    }
}
