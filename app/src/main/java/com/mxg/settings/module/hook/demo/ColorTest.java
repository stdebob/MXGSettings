// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.demo;

import android.graphics.Color;

import com.mxg.settings.module.base.BaseHook;

public class ColorTest extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.hchen.demo.MainActivity", "setColor",
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        mResHook.setObjectReplacement("com.hchen.demo", "color", "my_test_color", Color.RED);
                    }
                }
        );
    }
}
