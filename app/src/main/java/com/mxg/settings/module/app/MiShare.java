// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.mishare.NoAutoTurnOff;
import com.mxg.settings.module.hook.mishare.UnlockTurboMode;

@HookExpand(pkg = "com.miui.mishare.connectivity", isPad = false, tarAndroid = 33)
public class MiShare extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(NoAutoTurnOff.INSTANCE, mPrefsMap.getBoolean("disable_mishare_auto_off")); // 禁用 10 分钟自动关闭
        initHook(UnlockTurboMode.INSTANCE, mPrefsMap.getBoolean("unlock_turbo_mode")); // 解锁极速传输模式
    }
}
