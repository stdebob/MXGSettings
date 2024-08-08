// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.util.ArraySet;

import com.mxg.settings.module.base.BaseHook;

public class PstedClipboard extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.server.clipboard.ClipboardService",
                "lambda$showAccessNotificationLocked$4",
                String.class, int.class, ArraySet.class,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(null);
                    }
                }
        );
    }
}
