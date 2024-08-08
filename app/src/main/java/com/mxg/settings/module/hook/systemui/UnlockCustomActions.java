// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import com.mxg.settings.module.base.BaseHook;

import java.util.ArrayList;

import de.robv.android.xposed.XposedHelpers;

public class UnlockCustomActions extends BaseHook {

    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromState$customActions$1",
                "invoke", Object.class
                , new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        Object INSTANCE = XposedHelpers.getStaticObjectField(
                                findClassIfExists("com.android.systemui.statusbar.notification.NotificationSettingsManager$Holder"),
                                "INSTANCE");
                        XposedHelpers.setObjectField(INSTANCE, "mHiddenCustomActionsList", new ArrayList<>());
                    }

                }
        );
    }
}
