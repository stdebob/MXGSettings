// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.personalassistant.BlurPersonalAssistant;
import com.mxg.settings.module.hook.personalassistant.BlurPersonalAssistantBackGround;
import com.mxg.settings.module.hook.personalassistant.DisableLiteVersion;
import com.mxg.settings.module.hook.personalassistant.EnableFoldWidget;
import com.mxg.settings.module.hook.personalassistant.SetTravelNotificationStatusBarInfoMaxWidth;
import com.mxg.settings.module.hook.personalassistant.UnlockWidgetCountLimit;

@HookExpand(pkg = "com.miui.personalassistant", isPad = false, tarAndroid = 33)
public class PersonalAssistant extends BaseModule {

    @Override
    public void handleLoadPackage() {
        // initHook(new BlurOverlay(), false);
        initHook(new DisableLiteVersion(), mPrefsMap.getBoolean("personal_assistant_disable_lite_version"));
        initHook(new EnableFoldWidget(), mPrefsMap.getBoolean("personal_assistant_fold_widget_enable"));
        initHook(new UnlockWidgetCountLimit(), mPrefsMap.getBoolean("personal_assistant_unlock_widget_count_limit"));

        if (mPrefsMap.getStringAsInt("personal_assistant_value", 0) == 2) {
            initHook(BlurPersonalAssistant.INSTANCE , true);
        } else if (mPrefsMap.getStringAsInt("personal_assistant_value", 0) == 1) {
            initHook(BlurPersonalAssistantBackGround.INSTANCE, true);
        }

        initHook(new SetTravelNotificationStatusBarInfoMaxWidth(), mPrefsMap.getInt("personal_assistant_set_tv_notif_info_max_width", 60) != 60);
    }

}
