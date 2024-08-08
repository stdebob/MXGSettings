// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.personalassistant;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class EnableFoldWidget extends BaseHook {

    Class<?> c;
    Class<?> m2;
    Class<?> ga;

    @Override
    public void init() {

        c = findClassIfExists("b.z.g");

        m2 = findClassIfExists("c.h.e.i.b");

        ga = findClassIfExists("c.h.e.p.ga");

        findAndHookMethod(c, "a", Context.class, String.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticObjectField(m2, "a", "fold");
            }
        });

    }
}
