// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.base.settings.development;

import static com.mxg.settings.utils.shell.ShellUtils.safeExecCommandWithRoot;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mxg.settings.R;
import com.mxg.settings.data.AppData;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.ContextUtils;
import com.mxg.settings.utils.DialogHelper;
import com.mxg.settings.utils.ThreadPoolManager;
import com.mxg.settings.utils.ToastHelper;
import com.mxg.settings.utils.shell.ShellInit;

import java.util.concurrent.ExecutorService;

import moralnorm.appcompat.app.AlertDialog;
import moralnorm.preference.Preference;

public class DevelopmentFragment extends SettingsPreferenceFragment implements Preference.OnPreferenceClickListener {

    Preference mCmdR;
    Preference mDeleteAllDexKitCache;

    public interface EditDialogCallback {
        void onInputReceived(String command);
    }

    @Override
    public int getContentResId() {
        return R.xml.prefs_development;
    }

    @Override
    public void initPrefs() {
        mCmdR = findPreference("prefs_key_development_cmd_r");
        mDeleteAllDexKitCache = findPreference("prefs_key_development_delete_all_dexkit_cache");
        mCmdR.setOnPreferenceClickListener(this);
        mDeleteAllDexKitCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        switch (preference.getKey()) {
            case "prefs_key_development_cmd_r" -> {
                showInDialog(new DevelopmentKillFragment.EditDialogCallback() {
                    @Override
                    public void onInputReceived(String command) {
                        showOutDialog(safeExecCommandWithRoot(command));
                    }
                });
            }
            case "prefs_key_development_delete_all_dexkit_cache" -> {
                DialogHelper.showDialog(getActivity(), R.string.warn, R.string.delete_all_dexkit_cache_desc, (dialog, which) -> {
                    DexKit.deleteAllCache(getActivity());
                    Toast.makeText(getActivity(), R.string.delete_all_dexkit_cache_success, Toast.LENGTH_LONG).show();
                });
            }
        }
        return true;
    }

    private void showInDialog(DevelopmentKillFragment.EditDialogCallback callback) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_dialog, null);
        EditText input = view.findViewById(R.id.title);
        new AlertDialog.Builder(getActivity())
                .setTitle("# root@HyperCeiler > Input")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String userInput = input.getText().toString();
                    if (userInput.isEmpty()) {
                        dialog.dismiss();
                        showInDialog(callback);
                        return;
                    }
                    callback.onInputReceived(userInput);
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showOutDialog(String show) {
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("# root@HyperCeiler > Output")
                .setMessage(show)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
