// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.mms.DisableAd;
import com.mxg.settings.module.hook.mms.DisableRiskTip;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.android.mms", isPad = false, tarAndroid = 33)
public class Mms extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(new DisableRiskTip(), mPrefsMap.getBoolean("mms_disable_fraud_risk_tip") || mPrefsMap.getBoolean("mms_disable_overseas_risk_tip"));
        initHook(new DisableAd(), mPrefsMap.getBoolean("mms_disable_ad"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
