// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.joyose.DisableCloudControl;
import com.mxg.settings.module.hook.joyose.EnableGpuTuner;

@HookExpand(pkg = "com.xiaomi.joyose", isPad = false, tarAndroid = 33)
public class Joyose extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DisableCloudControl(), mPrefsMap.getBoolean("various_disable_cloud_control"));
        initHook(new EnableGpuTuner(), mPrefsMap.getBoolean("joyose_enable_gpu_tuner"));
    }
}
