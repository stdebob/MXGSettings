// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

public class MiWallpaperFragment extends SettingsPreferenceFragment {
    @Override
    public int getContentResId() {
        return R.xml.miwallpaper;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }
}
