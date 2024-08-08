// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.misound.BluetoothListener;
import com.mxg.settings.module.hook.misound.IncreaseSamplingRate;

@HookExpand(pkg = "com.miui.misound", isPad = false, tarAndroid = 33)
public class MiSound extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new BluetoothListener(), mPrefsMap.getBoolean("misound_bluetooth"));
        initHook(IncreaseSamplingRate.INSTANCE, mPrefsMap.getBoolean("misound_increase_sampling_rate"));
    }
}
