// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.base.settings.development;

import com.mxg.settings.expansionpacks.utils.SignUtils;

import static com.mxg.settings.utils.Helpers.isModuleActive;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getBoard;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getBrand;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getCharacteristics;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getDeviceName;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getDeviceToken;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getFingerPrint;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getLanguage;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getLocale;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getManufacture;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getMarketName;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getModDevice;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getModelName;
// import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getSerial;
import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getSoc;
import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isInternational;
import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getBuildDate;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getBuilder;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getHyperOSVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getMiuiVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getRomAuthor;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getSystemVersionIncremental;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.getWhoAmI;
import static com.mxg.settings.utils.log.LogManager.IS_LOGGER_ALIVE;
import static com.mxg.settings.utils.log.LogManager.LOGGER_CHECKER_ERR_CODE;

import com.mxg.settings.BuildConfig;
import com.mxg.settings.R;
import com.mxg.settings.ui.MainActivityContextHelper;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.api.ProjectApi;
import com.mxg.settings.utils.shell.ShellInit;

import java.util.LinkedHashMap;
import java.util.Map;

import moralnorm.preference.Preference;

public class DevelopmentDebugInfoFragment extends SettingsPreferenceFragment {

    private Preference mDebugInfo;
    MainActivityContextHelper mainActivityContextHelper;

    @Override
    public int getContentResId() {
        return R.xml.prefs_development_debug_info;
    }

    @Override
    public void initPrefs() {
        mDebugInfo = findPreference("prefs_key_debug_info");
        mainActivityContextHelper = new MainActivityContextHelper(requireContext());
        if (mDebugInfo != null) {
            mDebugInfo.setTitle(getDebugInfo());
        }
    }

    public String getDebugInfo() {
        Map<String, String> propertiesModule = new LinkedHashMap<>();
        Map<String, String> propertiesDevice = new LinkedHashMap<>();
        Map<String, String> propertiesSystem = new LinkedHashMap<>();
        Map<String, String> propertiesCheck = new LinkedHashMap<>();
        try {
            propertiesModule.put("VersionName", BuildConfig.VERSION_NAME);
            propertiesModule.put("VersionCode", String.valueOf(BuildConfig.VERSION_CODE));
            propertiesModule.put("BuildTime", "(UTC+0:00) " + BuildConfig.BUILD_TIME);
            propertiesModule.put("BuildType", BuildConfig.BUILD_TYPE);
            propertiesModule.put("GitHash", BuildConfig.GIT_HASH);
            propertiesModule.put("GitCode", BuildConfig.GIT_CODE);
            propertiesModule.put("Debug", String.valueOf(BuildConfig.DEBUG));
            propertiesModule.put("ApplicationId", ProjectApi.mAppModulePkg);
        } catch (Exception ignored) {
        }
        try {
            propertiesDevice.put("MarketName", getMarketName());
            propertiesDevice.put("DeviceName", getDeviceName());
            propertiesDevice.put("Model", getModelName());
            propertiesDevice.put("Brand", getBrand());
            propertiesDevice.put("Manufacture", getManufacture());
            propertiesDevice.put("Board", getBoard());
            propertiesDevice.put("Soc", getSoc());
            propertiesDevice.put("ModDevice", getModDevice());
            propertiesDevice.put("Characteristics", getCharacteristics());
            propertiesDevice.put("Pad", String.valueOf(isPad()));
            propertiesDevice.put("FingerPrint", getFingerPrint());
            propertiesDevice.put("Locale", getLocale());
            propertiesDevice.put("Language", getLanguage());
            propertiesDevice.put("AndroidId", mainActivityContextHelper.getAndroidId());
            // propertiesDevice.put("Serial", getSerial());
            propertiesDevice.put("DeviceToken", getDeviceToken(mainActivityContextHelper.getAndroidId()));
        } catch (Exception ignored) {
        }
        try {
            propertiesSystem.put("AndroidSdkVersion", String.valueOf(getAndroidVersion()));
            propertiesSystem.put("MiuiVersion", String.valueOf(getMiuiVersion()));
            propertiesSystem.put("HyperOsVersion", String.valueOf(getHyperOSVersion()));
            propertiesSystem.put("SystemVersion", getSystemVersionIncremental());
            propertiesSystem.put("InternationalBuild", String.valueOf(isInternational()));
            propertiesSystem.put("Builder", getBuilder());
            propertiesSystem.put("RomAuthor", getRomAuthor());
            propertiesSystem.put("BaseOs", com.mxg.settings.utils.devicesdk.SystemSDKKt.getBaseOs());
            propertiesSystem.put("Host", com.mxg.settings.utils.devicesdk.SystemSDKKt.getHost());
            propertiesSystem.put("BuildDate", getBuildDate());
        } catch (Exception ignored) {
        }
        try {
            propertiesCheck.put("Signature", SignUtils.getSHA256Signature(requireContext()));
            propertiesCheck.put("SignCheckPass", String.valueOf(SignUtils.isSignCheckPass(requireContext())));
            propertiesCheck.put("ModuleActive", String.valueOf(isModuleActive));
            propertiesCheck.put("RootPermission", String.valueOf(ShellInit.ready()));
            propertiesCheck.put("WhoAmI", getWhoAmI());
            propertiesCheck.put("LoggerStatus", IS_LOGGER_ALIVE + ", " + LOGGER_CHECKER_ERR_CODE);
        } catch (Exception ignored) {
        }

        StringBuilder debugInfo = new StringBuilder("Debug Info by HyperCeiler");
        debugInfo.append("\n");
        for (Map.Entry<String, String> entry : propertiesModule.entrySet()) {
            debugInfo.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        debugInfo.append("\n");
        for (Map.Entry<String, String> entry : propertiesDevice.entrySet()) {
            debugInfo.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        debugInfo.append("\n");
        for (Map.Entry<String, String> entry : propertiesSystem.entrySet()) {
            debugInfo.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        debugInfo.append("\n");
        for (Map.Entry<String, String> entry : propertiesCheck.entrySet()) {
            debugInfo.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        return debugInfo.toString();
    }
}
