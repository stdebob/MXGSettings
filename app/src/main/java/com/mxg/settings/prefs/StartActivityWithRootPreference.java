// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.prefs;

import static com.mxg.settings.utils.log.XposedLogUtils.logE;
import static com.mxg.settings.utils.shell.ShellUtils.checkRootPermission;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.mxg.settings.R;

import java.io.DataOutputStream;
import java.io.IOException;

import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceViewHolder;

public class StartActivityWithRootPreference extends Preference {

    private String targetActivityClass;

    public StartActivityWithRootPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        targetActivityClass = attrs.getAttributeValue("http://schemas.android.com/apk/res-custom", "targetActivityClass");
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        holder.itemView.setOnClickListener(v -> {
            launchActivityWithRoot();
        });
    }

    private void launchActivityWithRoot() {
        if (checkRootPermission() == 0) {
            try {
                Process suProcess = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
                os.writeBytes("am start -n " + targetActivityClass + "\n");
                os.flush();
                os.writeBytes("exit\n");
                os.flush();
                suProcess.waitFor();
            } catch (IOException | InterruptedException e) {
                logE("StartActivityWithRootPreference", "com.mxg.settings", "Failed to start activity \"" + targetActivityClass + "\" with root", e);
            }
        } else {
            Toast.makeText(this.getContext(), R.string.start_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
