// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.nfc;

import com.mxg.settings.module.base.BaseHook;

public class DisableSound extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.nfc.NfcService",
            "initSoundPool", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(null);
                }
            }
        );
    }
}
