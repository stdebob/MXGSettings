// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.hook.personalassistant;

import com.mxg.settings.module.base.BaseHook;

public class SetTravelNotificationStatusBarInfoMaxWidth extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        mResHook.setDensityReplacement("com.miui.personalassistant", "dimen", "pa_travel_notification_statusbar_info_max_width", (float) mPrefsMap.getInt("personal_assistant_set_tv_notif_info_max_width", 60));
    }
}
