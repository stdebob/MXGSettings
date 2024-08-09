// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.prefs;

import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getLanguage;
import static com.mxg.settings.utils.log.XposedLogUtils.logE;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mxg.settings.ui.MainActivityContextHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import moralnorm.preference.Preference;

public class TipsPreference extends Preference {

    Context mContext;
    MainActivityContextHelper mainActivityContextHelper;

    public TipsPreference(@NonNull Context context) {
        this(context, null);
    }

    public TipsPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipsPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setEnabled(false);
        updateTips();
    }

        try {
            InputStream inputStream;
            try {
                inputStream = assetManager.open(fileName);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("//")) {
                    tipsList.add(line);
                }
            }

            reader.close();
            inputStream.close();

    }
}
