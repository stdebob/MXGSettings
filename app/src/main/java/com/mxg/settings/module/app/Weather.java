// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.weather.SetCardLightDarkMode;
import com.mxg.settings.module.hook.weather.SetDeviceLevel;

@HookExpand(pkg = "com.miui.weather2", isPad = false, tarAndroid = 33)
public class Weather extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new SetCardLightDarkMode(), mPrefsMap.getStringAsInt("weather_card_display_type", 0) != 0);
        initHook(new SetDeviceLevel(), mPrefsMap.getStringAsInt("weather_device_level", 3) != 3);
    }
}
