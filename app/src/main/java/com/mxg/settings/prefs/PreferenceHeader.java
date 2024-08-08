// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.prefs;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mxg.settings.R;
import com.mxg.settings.utils.PackagesUtils;

import java.util.ArrayList;

import moralnorm.preference.Preference;

public class PreferenceHeader extends Preference {

    public static ArrayList<String> mUninstallApp = new ArrayList<>();
    public static ArrayList<String> mDisableOrHiddenApp = new ArrayList<>();

    public PreferenceHeader(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PreferenceHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setLayoutResource(R.layout.preference_header);
        if (isUninstall(context)) {
            mUninstallApp.add(" - " + getTitle() + " (" + getSummary() + ")");
            setVisible(false);
        } else {
            if (isDisable(context) || isHidden(context)) {
                mDisableOrHiddenApp.add(" - " + getTitle() + " (" + getSummary() + ")");
                setVisible(false);
            }
        }
    }

    private boolean isUninstall(Context context) {
        if (getSummary() == null || "android".contentEquals(getSummary())) return false;
        return PackagesUtils.isUninstall(context, (String) getSummary());
    }

    private boolean isDisable(Context context) {
        if (getSummary() == null || "android".contentEquals(getSummary())) return false;
        return PackagesUtils.isDisable(context, (String) getSummary());
    }

    private boolean isHidden(Context context) {
        if (getSummary() == null || "android".contentEquals(getSummary())) return false;
        return PackagesUtils.isHidden(context, (String) getSummary());
    }
}
