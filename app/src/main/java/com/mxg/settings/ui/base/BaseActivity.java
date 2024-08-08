// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.base;

import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.mxg.settings.R;
import com.mxg.settings.provider.SharedPrefsProvider;
import com.mxg.settings.utils.Helpers;
import com.mxg.settings.utils.prefs.PrefsUtils;

import java.util.Set;

import moralnorm.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseSettingsProxy mProxy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mProxy = new SettingsProxy(this);
        super.onCreate(savedInstanceState);
        initActionBar();
        registerObserver();
    }

    protected void initActionBar() {
        setDisplayHomeAsUpEnabled(!(this instanceof NavigationActivity));
    }

    public void setDisplayHomeAsUpEnabled(boolean isEnable) {
        getAppCompatActionBar().setDisplayHomeAsUpEnabled(isEnable);
    }

    public void setActionBarEndView(View view) {
        getAppCompatActionBar().setEndView(view);
    }

    public void setActionBarEndIcon(@DrawableRes int resId, View.OnClickListener listener) {
        ImageView mRestartView = new ImageView(this);
        mRestartView.setImageResource(resId);
        mRestartView.setOnClickListener(listener);
        setActionBarEndView(mRestartView);
    }

    public void setRestartView(View.OnClickListener listener) {
        if (listener != null) setActionBarEndIcon(R.drawable.ic_reboot_small, listener);
    }

    private void registerObserver() {
        PrefsUtils.mSharedPreferences.registerOnSharedPreferenceChangeListener(mSharedPreferenceChangeListener);
        Helpers.fixPermissionsAsync(getApplicationContext());
        registerFileObserver();
    }

    SharedPreferences.OnSharedPreferenceChangeListener mSharedPreferenceChangeListener = (sharedPreferences, s) -> {
        Log.i("prefs", "Changed: " + s);
        requestBackup();
        Object val = sharedPreferences.getAll().get(s);
        String path = "";
        if (val instanceof String)
            path = "string/";
        else if (val instanceof Set<?>)
            path = "stringset/";
        else if (val instanceof Integer)
            path = "integer/";
        else if (val instanceof Boolean)
            path = "boolean/";
        getContentResolver().notifyChange(Uri.parse("content://" + SharedPrefsProvider.AUTHORITY + "/" + path + s), null);
        if (!path.isEmpty()) getContentResolver().notifyChange(Uri.parse("content://" + SharedPrefsProvider.AUTHORITY + "/pref/" + path + s), null);
    };

    private void registerFileObserver() {
        try {
            FileObserver mFileObserver = new FileObserver(PrefsUtils.getSharedPrefsPath(), FileObserver.CLOSE_WRITE) {
                @Override
                public void onEvent(int event, String path) {
                    Helpers.fixPermissionsAsync(getApplicationContext());
                }
            };
            mFileObserver.startWatching();
        } catch (Throwable t) {
            Log.e("prefs", "Failed to start FileObserver!");
        }
    }

    public void requestBackup() {
        new BackupManager(getApplicationContext()).dataChanged();
    }
}
