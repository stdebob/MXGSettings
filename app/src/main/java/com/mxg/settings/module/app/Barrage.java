// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.barrage.AnyBarrage;
import com.mxg.settings.module.hook.barrage.CustomBarrageLength;

@HookExpand(pkg = "com.xiaomi.barrage", isPad = false, tarAndroid = 33)
public class Barrage extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(AnyBarrage.INSTANCE, mPrefsMap.getBoolean("barrage_any_barrage"));
        initHook(CustomBarrageLength.INSTANCE, mPrefsMap.getInt("barrage_custom_barrage_length", 36) != 36);
    }
}
