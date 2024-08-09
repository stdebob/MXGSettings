// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import static com.mxg.settings.module.base.tool.OtherTool.getModuleRes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;

import com.mxg.settings.R;
import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.ui.MainActivity;
import com.mxg.settings.utils.api.ProjectApi;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;
import moralnorm.os.Build;

public class MXGSettings extends BaseHook {

    private int settingsIconResId;

    private Class<?> mPreferenceHeader;

    @Override
    public void init() {
        addIconResource();

        Class<?> mMiuiSettings = findClassIfExists("com.android.settings.MiuiSettings");

        findAndHookMethod(mMiuiSettings, "updateHeaderList", List.class, new MethodHook() {
            @Override
            @SuppressLint("DiscouragedApi")
            protected void after(MethodHookParam param) throws Throwable {
                if (param.args[0] == null) return;

                Context mContext = ((Activity) param.thisObject).getBaseContext();
                int opt = Integer.parseInt(mPrefsMap.getString("settings_icon", "0"));
                if (opt == 0) return;

                Resources modRes = getModuleRes(mContext);
                mPreferenceHeader = findClassIfExists("com.android.settingslib.miuisettings.preference.PreferenceActivity$Header");
                if (mPreferenceHeader == null) return;

                Intent mIntent = new Intent();
                mIntent.putExtra("isDisplayHomeAsUpEnabled", true);
                mIntent.setClassName(ProjectApi.mAppModulePkg, MainActivity.class.getCanonicalName());

                Object header = XposedHelpers.newInstance(mPreferenceHeader);
                XposedHelpers.setLongField(header, "id", 666);
                XposedHelpers.setObjectField(header, "intent", mIntent);
                XposedHelpers.setIntField(header, "iconRes", settingsIconResId);
                XposedHelpers.setObjectField(header, "title", modRes.getString(R.string.app_name));

                Bundle bundle = new Bundle();
                ArrayList<UserHandle> users = new ArrayList<>();
                users.add((UserHandle) XposedHelpers.newInstance(UserHandle.class, 0));
                bundle.putParcelableArrayList("header_user", users);
                XposedHelpers.setObjectField(header, "extras", bundle);

                int themes = mContext.getResources().getIdentifier("launcher_settings", "id", mContext.getPackageName());
                int special = mContext.getResources().getIdentifier("other_special_feature_settings", "id", mContext.getPackageName());
                int timer = mContext.getResources().getIdentifier("app_timer", "id", mContext.getPackageName());

                List<Object> headers = (List<Object>) param.args[0];
                int position = 0;
                for (Object head : headers) {
                    position++;
                    long id = XposedHelpers.getLongField(head, "id");
                    headers.add(position - 1, header);
                }
                if (headers.size() > 25) {
                    headers.add(25, header);
                } else {
                    headers.add(header);
                }
            }
        });
    }


    private void addIconResource() {
        if (Integer.parseInt(Build.getMiuiVersionCode()) < 816) {
            settingsIconResId = R.drawable.ic_mxgsettings_miui;
        } else {
            settingsIconResId = R.drawable.ic_mxgsettings_hos;
        }
    }
}
