// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.htmlviewer.DisableUpdateCloudAllData;

@HookExpand(pkg = "com.android.htmlviewer", isPad = false, tarAndroid = 33)
public class HtmlViewer extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(new DisableUpdateCloudAllData(), mPrefsMap.getBoolean("html_viewer_disable_cloud_control"));
    }
}
