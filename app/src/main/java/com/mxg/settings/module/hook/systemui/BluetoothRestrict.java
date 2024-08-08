// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class BluetoothRestrict extends BaseHook {

    Class<?> mLocalBluetoothAdapter;

    @Override
    public void init() {
        mLocalBluetoothAdapter = findClassIfExists("com.android.settingslib.bluetooth.LocalBluetoothAdapter");

        findAndHookMethod(mLocalBluetoothAdapter,
            "isSupportBluetoothRestrict",
            Context.class,
            XC_MethodReplacement.returnConstant(false));
    }
}
