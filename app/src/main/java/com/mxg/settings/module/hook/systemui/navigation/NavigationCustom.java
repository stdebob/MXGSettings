// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.navigation;

import com.mxg.settings.module.base.BaseHook;


public class NavigationCustom extends BaseHook {
    @Override
    public void init() {

        float mNavigationHeight = ((float) mPrefsMap.getInt("system_ui_navigation_custom_height", 100) / 10);
        float mNavigationHeightLand = ((float) mPrefsMap.getInt("system_ui_navigation_custom_height_land", 100) / 10);
        float mNavigationFrameHeight = ((float) mPrefsMap.getInt("system_ui_navigation_frame_custom_height", 100) / 10);
        float mNavigationFrameHeightLand = ((float) mPrefsMap.getInt("system_ui_navigation_frame_custom_height_land", 100) / 10);

        try {
            mResHook.setDensityReplacement("*", "dimen", "navigation_bar_height", mNavigationHeight);
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "navigation_bar_height error", e);
        }
        try {
            mResHook.setDensityReplacement("*", "dimen", "navigation_bar_height_landscape", mNavigationHeightLand);
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "navigation_bar_height_landscape error", e);
        }
        try {
            mResHook.setDensityReplacement("*", "dimen", "navigation_bar_frame_height", mNavigationFrameHeight);
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "navigation_bar_frame_height error", e);
        }
        try {
            mResHook.setDensityReplacement("*", "dimen", "navigation_bar_frame_height_landscape", mNavigationFrameHeightLand);
        } catch (Exception e) {
            logE(TAG, this.lpparam.packageName, "navigation_bar_frame_height_landscape error", e);
        }

    }
}
