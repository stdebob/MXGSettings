// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import android.content.ComponentName;
import android.content.Intent;

import com.mxg.settings.module.base.BaseHook;

public class RunningServices extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.settings.SettingsActivity",
            "getStartingFragmentClass", Intent.class,
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    Intent intent = (Intent) param.args[0];
                    ComponentName componentName = intent.getComponent();
                    if (componentName != null) {
                        String className = componentName.getClassName();
                        if ("com.android.settings.RunningServices".equals(className)) {
                            param.setResult("com.android.settings.applications.RunningServices");
                        }
                    }
                }
            }
        );
    }
}
