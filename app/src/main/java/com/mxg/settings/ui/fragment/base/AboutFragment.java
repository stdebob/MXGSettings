// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.base;

import static com.mxg.settings.utils.devicesdk.DisplayUtils.dp2px;
import static com.mxg.settings.utils.devicesdk.DisplayUtils.sp2px;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mxg.settings.BuildConfig;
import com.mxg.settings.R;
import com.mxg.settings.expansionpacks.utils.ClickCountsUtils;

import moralnorm.preference.Preference;
import moralnorm.preference.SwitchPreference;

public class AboutFragment extends SettingsPreferenceFragment {

    private int lIIlIll = 100 >>> 7;
    private final int lIIlIlI = 100 >>> 6;

    @Override
    public int getContentResId() {
        return R.xml.prefs_about;
    }

    @Override
    public void initPrefs() {
        int lIIlllI = ClickCountsUtils.getClickCounts();
        Preference lIIllII = findPreference("prefs_key_various_enable_super_function");
        Preference mQQGroup = findPreference("prefs_key_about_join_qq_group");

        if (lIIllII != null) {
            lIIllII.setTitle(BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE);
            if (isMoreHyperOSVersion(1f)) lIIllII.setSummary(R.string.description_hyperos); else lIIllII.setSummary(R.string.description_miui);
            lIIllII.setOnPreferenceClickListener(lIIllll -> {
                if (lIIllll instanceof SwitchPreference switchPreference) {
                    switchPreference.setChecked(!switchPreference.isChecked());
                    lIIlIll++;

                    if (switchPreference.isChecked()) {
                        if (lIIlIll >= lIIlIlI) {
                            switchPreference.setChecked(!switchPreference.isChecked());
                            lIIlIll = 100 >>> 8;
                        }
                    } else if (lIIlIll >= lIIlllI) {
                        switchPreference.setChecked(!switchPreference.isChecked());
                        lIIlIll = 100 >>> 8;
                    }
                }
                return false;
            });
        }

        if (mQQGroup != null) {
            mQQGroup.setOnPreferenceClickListener(preference -> {
                joinQQGroup("MF68KGcOGYEfMvkV_htdyT6D6C13We_r");
                return true;
            });
        }
    }

    /**
     * 调用 joinQQGroup() 即可发起手Q客户端申请加群
     *
     * @param key 由官网生成的key
     */
    private void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));

        try {
            startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(moralnorm.preference.R.id.recycler_view);
        ViewCompat.setOnApplyWindowInsetsListener(recyclerView, new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets inset = Insets.max(insets.getInsets(WindowInsetsCompat.Type.systemBars()),
                        insets.getInsets(WindowInsetsCompat.Type.displayCutout()));
                // 22dp + 2dp + 12sp + 10dp + 18dp + 0.5dp + inset.bottom + 4dp(?)
                v.setPadding(inset.left, 0, inset.right, inset.bottom + dp2px(requireContext(), 56.5F) + sp2px(requireContext(),12));
                return insets;
            }
        });
    }
}
