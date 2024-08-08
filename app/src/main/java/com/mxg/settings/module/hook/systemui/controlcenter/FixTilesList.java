// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import com.mxg.settings.R;
import com.mxg.settings.module.base.BaseHook;

public class FixTilesList extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        mResHook.setResReplacement("com.android.systemui", "string", "miui_quick_settings_tiles_stock", R.string.miui_quick_settings_tiles_stock);
        mResHook.setResReplacement("com.android.systemui", "string", "miui_quick_settings_tiles_stock_pad", R.string.miui_quick_settings_tiles_stock_pad);
    }
}
