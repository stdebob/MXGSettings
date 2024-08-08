// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui;

import android.content.Intent;

import com.mxg.settings.callback.IAppSelectCallback;
import com.mxg.settings.ui.base.SettingsActivity;
import com.mxg.settings.ui.fragment.sub.AppPicker;

public class SubPickerActivity extends SettingsActivity {
    AppPicker mAppSelectFragment = new AppPicker();

    @Override
    public void initCreate() {
        mAppSelectFragment.setAppSelectCallback(new IAppSelectCallback() {
            @Override
            public void sendMsgToActivity(byte[] appIcon, String appName, String appPackageName, String appVersion, String appActivityName) {
                Intent intent = new Intent();
                intent.putExtra("appIcon", appIcon);
                intent.putExtra("appName", appName);
                intent.putExtra("appPackageName", appPackageName);
                intent.putExtra("appVersion", appVersion);
                intent.putExtra("appActivityName", appActivityName);
                setResult(1, intent);
            }
            @Override
            public String getMsgFromActivity(String s) {
                return null;
            }
        });
        setFragment(mAppSelectFragment);
    }
}
