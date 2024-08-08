// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.phone.DisableRemoveNetworkMode;
import com.mxg.settings.module.hook.phone.DualNrSupport;
import com.mxg.settings.module.hook.phone.DualSaSupport;
import com.mxg.settings.module.hook.phone.ModemFeature;
import com.mxg.settings.module.hook.phone.N1BandPhone;
import com.mxg.settings.module.hook.phone.N28BandPhone;
import com.mxg.settings.module.hook.phone.N5N8BandPhone;
import com.mxg.settings.module.hook.phone.ViceSlotVolteButton;

@HookExpand(pkg = "com.android.phone", isPad = false, tarAndroid = 33)
public class Phone extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(ModemFeature.INSTANCE, mPrefsMap.getBoolean("phone_smart_dual_sim"));
        initHook(ViceSlotVolteButton.INSTANCE, mPrefsMap.getBoolean("phone_vice_slot_volte"));
        initHook(new DisableRemoveNetworkMode(), mPrefsMap.getBoolean("phone_disable_remove_network_mode"));

        initHook(DualNrSupport.INSTANCE, mPrefsMap.getBoolean("phone_double_5g_nr"));
        initHook(DualSaSupport.INSTANCE, mPrefsMap.getBoolean("phone_double_5g_sa"));
        initHook(N1BandPhone.INSTANCE, mPrefsMap.getBoolean("phone_n1"));
        initHook(N5N8BandPhone.INSTANCE, mPrefsMap.getBoolean("phone_n5_n8"));
        initHook(N28BandPhone.INSTANCE, mPrefsMap.getBoolean("phone_n28"));
    }
}
