// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.widget.TextView;

import com.mxg.settings.module.base.BaseHook;

public class TitleFontSize extends BaseHook {

    @Override
    public void init() {
        hookAllMethods("com.miui.home.launcher.common.Utilities", "adaptTitleStyleToWallpaper",
            new MethodHook() {
                @SuppressLint("DiscouragedApi")
                @Override
                protected void after(MethodHookParam param) {
                    TextView mTitle = (TextView) param.args[1];
                    if (mTitle != null && mTitle.getId() == mTitle.getResources().getIdentifier("icon_title", "id", "com.miui.home")) {
                        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mPrefsMap.getInt("home_title_font_size", 12));
                    }
                }
            }
        );
    }
}
