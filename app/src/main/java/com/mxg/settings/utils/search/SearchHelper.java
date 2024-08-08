/*
 * This file is part of HyperCeiler.

 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.

 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */
package com.mxg.settings.utils.search;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.text.TextUtils;

import com.mxg.settings.R;
import com.mxg.settings.data.ModData;
import com.mxg.settings.utils.log.AndroidLogUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchHelper {

    public static final int MARK_COLOR_VIBRANT = Color.rgb(255, 0, 0);
    public static final String NEW_MODS_SEARCH_QUERY = "\uD83C\uDD95";
    public static List<ModData> allModsList = new ArrayList<>();

    public static String TAG = "SearchHelper";

    public static final HashSet<String> NEW_MODS = new HashSet<>(
            Set.of(
                    "pref_key_launcher_nozoomanim"
            )
    );

    public static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    public static final String APP_NS = "http://schemas.android.com/apk/res-auto";

    public static void getAllMods(Context context, boolean force) {
        if (force) {
            allModsList.clear();
        } else if (!allModsList.isEmpty()) {
            return;
        }
        // 系统框架页面相关
        parsePrefXml(context, R.xml.framework_freeform,
                "com.mxg.settings.ui.fragment.framework.FreeFormSettings",
                R.string.system_framework
        );

        parsePrefXml(context, R.xml.framework_volume,
                "com.mxg.settings.ui.fragment.framework.VolumeSettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_phone,
                "com.mxg.settings.ui.fragment.framework.NetworkSettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_display,
                "com.mxg.settings.ui.fragment.framework.DisplaySettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_other,
                "com.mxg.settings.ui.fragment.framework.OtherSettings",
                R.string.system_framework);

        // 系统界面页面相关
        parsePrefXml(context, R.xml.system_ui_lock_screen,
                "com.mxg.settings.ui.fragment.systemui.LockScreenSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_status_bar,
                "com.mxg.settings.ui.fragment.systemui.StatusBarSettings",
                R.string.system_ui);

        parsePrefXml(context, !isMoreHyperOSVersion(1f) ? R.xml.system_ui_status_bar_icon_manage : R.xml.system_ui_status_bar_icon_manage_new,
                !isMoreHyperOSVersion(1f) ? "com.mxg.settings.ui.fragment.systemui.statusbar.IconManageSettings" : "com.mxg.settings.ui.fragment.systemui.statusbar.IconManageNewSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_mobile_network_type,
                "com.mxg.settings.ui.fragment.systemui.statusbar.MobileNetworkTypeSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_doubleline_network,
                "com.mxg.settings.ui.fragment.systemui.statusbar.DoubleLineNetworkSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_battery_styles,
                "com.mxg.settings.ui.fragment.systemui.statusbar.BatteryStyleSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_network_speed_indicator,
                "com.mxg.settings.ui.fragment.systemui.statusbar.NetworkSpeedIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, !isMoreHyperOSVersion(1f) ? R.xml.system_ui_status_bar_clock_indicator : R.xml.system_ui_status_bar_new_clock_indicator,
                !isMoreHyperOSVersion(1f) ? "com.mxg.settings.ui.fragment.systemui.statusbar.ClockIndicatorSettings" : "com.mxg.settings.ui.fragment.systemui.statusbar.NewClockIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        // 这里
        parsePrefXml(context, R.xml.system_ui_status_bar_hardware_detail_indicator,
                "com.mxg.settings.ui.fragment.systemui.statusbar.BatteryDetailIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_strong_toast,
                "com.mxg.settings.ui.fragment.systemui.statusbar.StrongToastSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_navigation,
                "com.mxg.settings.ui.fragment.systemui.NavigationSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_control_center,
                "com.mxg.settings.ui.fragment.systemui.ControlCenterSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_other,
                "com.mxg.settings.ui.fragment.systemui.SystemUIOtherSettings",
                R.string.system_ui);

        // 系统桌面相关
        parsePrefXml(context, R.xml.home_gesture,
                "com.mxg.settings.ui.fragment.home.HomeGestureSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_layout,
                "com.mxg.settings.ui.fragment.home.HomeLayoutSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_folder,
                "com.mxg.settings.ui.fragment.home.HomeFolderSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_drawer,
                "com.mxg.settings.ui.fragment.home.HomeDrawerSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_title,
                "com.mxg.settings.ui.fragment.home.HomeTitleSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_title_anim,
                "com.mxg.settings.ui.fragment.home.HomeTitleAnimSettings",
                R.string.mihome,
                R.string.home_title);

        parsePrefXml(context, R.xml.home_title_anim_1,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim1Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_2,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim2Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_3,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim3Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_4,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim4Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_5,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim5Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_6,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnimSettings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_7,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim7Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_8,
                "com.mxg.settings.ui.fragment.home.anim.HomeTitleAnim8Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_recent,
                "com.mxg.settings.ui.fragment.home.HomeRecentSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_widget,
                "com.mxg.settings.ui.fragment.home.HomeWidgetSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_dock,
                "com.mxg.settings.ui.fragment.home.HomeDockSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_other,
                "com.mxg.settings.ui.fragment.home.HomeOtherSettings",
                R.string.mihome);

        // 设置相关
        parsePrefXml(context, R.xml.system_settings,
                "com.mxg.settings.ui.fragment.SystemSettingsFragment");

        // 其他杂项
        parsePrefXml(context, R.xml.browser,
                "com.mxg.settings.ui.fragment.BrowserFragment");

        parsePrefXml(context, !isMoreHyperOSVersion(1f) ? R.xml.camera : R.xml.camera_new,
                !isMoreHyperOSVersion(1f) ? "com.mxg.settings.ui.fragment.CameraFragment" : "com.mxg.settings.ui.fragment.CameraNewFragment");

        parsePrefXml(context, R.xml.fileexplorer,
                "com.mxg.settings.ui.fragment.FileExplorerFragment");

        parsePrefXml(context, R.xml.incallui,
                "com.mxg.settings.ui.fragment.InCallUiFragment");

        parsePrefXml(context, R.xml.mms,
                "com.mxg.settings.ui.fragment.MmsFragment");

        parsePrefXml(context, R.xml.remotecontroller,
                "com.mxg.settings.ui.fragment.RemoteControllerFragment");

        parsePrefXml(context, R.xml.nfc,
                "com.mxg.settings.ui.fragment.NfcFragment");

        parsePrefXml(context, R.xml.phone,
                "com.mxg.settings.ui.fragment.PhoneFragment");

        parsePrefXml(context, R.xml.downloads,
                "com.mxg.settings.ui.fragment.DownloadsFragment");

        parsePrefXml(context, R.xml.updater,
                "com.mxg.settings.ui.fragment.UpdaterFragment");

        if (!isMoreHyperOSVersion(1f)) {
            parsePrefXml(context, R.xml.lbe_security,
                    "com.mxg.settings.ui.fragment.LbeFragment");
        }

        parsePrefXml(context, R.xml.milink,
                "com.mxg.settings.ui.fragment.MiLinkFragment");

        parsePrefXml(context, R.xml.aod,
                "com.mxg.settings.ui.fragment.AodFragment");

        parsePrefXml(context, R.xml.content_extension,
                "com.mxg.settings.ui.fragment.ContentExtensionFragment");

        parsePrefXml(context, R.xml.gallery,
                "com.mxg.settings.ui.fragment.GalleryFragment");

        parsePrefXml(context, R.xml.guard_provider,
                "com.mxg.settings.ui.fragment.GuardProviderFragment");

        parsePrefXml(context, R.xml.mediaeditor,
                "com.mxg.settings.ui.fragment.MediaEditorFragment");

        parsePrefXml(context, R.xml.mishare,
                "com.mxg.settings.ui.fragment.MiShareFragment");

        parsePrefXml(context, R.xml.miwallpaper,
                "com.mxg.settings.ui.fragment.MiWallpaperFragment");

        parsePrefXml(context, R.xml.package_installer,
                "com.mxg.settings.ui.fragment.MiuiPackageInstallerFragment");

        parsePrefXml(context, R.xml.powerkeeper,
                "com.mxg.settings.ui.fragment.PowerKeeperFragment");

        parsePrefXml(context, R.xml.screenrecorder,
                "com.mxg.settings.ui.fragment.ScreenRecorderFragment");

        parsePrefXml(context, R.xml.screenshot,
                "com.mxg.settings.ui.fragment.ScreenShotFragment");

        parsePrefXml(context, R.xml.security_center_app,
                "com.mxg.settings.ui.fragment.securitycenter.ApplicationsSettings",
                R.string.security_center);

        parsePrefXml(context, R.xml.security_center_battery,
                "com.mxg.settings.ui.fragment.securitycenter.BatterySettings",
                R.string.security_center);

        parsePrefXml(context, R.xml.security_center_privacy_safety,
                "com.mxg.settings.ui.fragment.securitycenter.PrivacySafetySettings",
                R.string.security_center);

        parsePrefXml(context, R.xml.security_center_sidebar,
                "com.mxg.settings.ui.fragment.securitycenter.SidebarSettings",
                R.string.security_center);

        parsePrefXml(context, R.xml.security_center_other,
                "com.mxg.settings.ui.fragment.securitycenter.OtherSettings",
                R.string.security_center);

        parsePrefXml(context, R.xml.tsmclient,
                "com.mxg.settings.ui.fragment.TsmClientFragment");

        parsePrefXml(context, R.xml.html_viewer,
                "com.mxg.settings.ui.fragment.HtmlViewerFragment");

        parsePrefXml(context, R.xml.weather,
                "com.mxg.settings.ui.fragment.WeatherFragment");

        parsePrefXml(context, R.xml.aiasst,
                "com.mxg.settings.ui.fragment.AiAsstFragment");

        parsePrefXml(context, R.xml.voicetrigger,
                "com.mxg.settings.ui.fragment.VoiceTriggerFragment");

        parsePrefXml(context, R.xml.telecom,
                "com.mxg.settings.ui.fragment.TelecomFragment");

        parsePrefXml(context, R.xml.tsmclient,
                "com.mxg.settings.ui.fragment.TsmClientFragment");

        parsePrefXml(context, R.xml.barrage,
                "com.mxg.settings.ui.fragment.BarrageFragment");

        parsePrefXml(context, R.xml.joyose,
                "com.mxg.settings.ui.fragment.JoyoseFragment");

        parsePrefXml(context, R.xml.getapps,
                "com.mxg.settings.ui.fragment.MarketFragment");

        parsePrefXml(context, R.xml.mtb,
                "com.mxg.settings.ui.fragment.MtbFragment");

        parsePrefXml(context, R.xml.scanner,
                "com.mxg.settings.ui.fragment.ScannerFragment");

        parsePrefXml(context, R.xml.micloud_service,
                "com.mxg.settings.ui.fragment.MiCloudServiceFragment");

        parsePrefXml(context, R.xml.creation,
                "com.mxg.settings.ui.fragment.CreationFragment");

        parsePrefXml(context, R.xml.huanji,
                "com.mxg.settings.ui.fragment.HuanjiFragment");

        parsePrefXml(context, R.xml.misound,
                "com.mxg.settings.ui.fragment.MiSoundFragment");

        parsePrefXml(context, R.xml.trustservice,
                "com.mxg.settings.ui.fragment.TrustServiceFragment");

        parsePrefXml(context, R.xml.calendar,
                "com.mxg.settings.ui.fragment.CalendarFragment");

        parsePrefXml(context, R.xml.securityadd,
                "com.mxg.settings.ui.fragment.SecurityAddFragment");

        parsePrefXml(context, R.xml.various,
                "com.mxg.settings.ui.fragment.VariousFragment");

        parsePrefXml(context, R.xml.various_aosp,
                "com.mxg.settings.ui.fragment.various.AOSPSettings",
                R.string.various);

        if (isPad()) {
            parsePrefXml(context, R.xml.various_mipad,
                    "com.mxg.settings.ui.fragment.VariousFragment",
                    R.string.various);
        }

        // 实验性
        parsePrefXml(context, R.xml.theme_manager,
                "com.mxg.settings.ui.fragment.ThemeManagerFragment");

        parsePrefXml(context, R.xml.personal_assistant,
                "com.mxg.settings.ui.fragment.PersonalAssistantFragment");
    }

    private static void parsePrefXml(Context context, int xmlResId, String catPrefsFragment, int... internalId) {
        Resources res = context.getResources();
        try (XmlResourceParser xml = res.getXml(xmlResId)) {
            int order = 0;
            String location = null;
            String locationHyper = null;
            String locationPad = null;
            int locationId = 0;
            int locationHyperId = 0;
            int locationPadId = 0;
            boolean isPad = isPad();
            StringBuilder internalName = null;
            int eventType = xml.getEventType();
            if (internalId.length != 0) {
                for (int id : internalId) {
                    String getString = res.getString(id);
                    if (internalName == null) {
                        internalName = new StringBuilder(getString);
                    } else {
                        internalName.append("/").append(getString);
                    }
                }
            }
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && !xml.getName().equals("PreferenceCategory")) {
                    try {
                        ModData modData = new ModData();
                        modData.title = getModTitle(res, xml.getAttributeValue(ANDROID_NS, "title"));
                        boolean isPreferenceVisible = Boolean.parseBoolean(xml.getAttributeValue(APP_NS, "isPreferenceVisible"));
                        if (locationHyper == null) {
                            locationHyper = getModTitle(res, xml.getAttributeValue(APP_NS, "myLocationHyper"));
                            locationHyperId = getModId(xml.getAttributeValue(APP_NS, "myLocationHyper"));
                        }
                        if (locationPad == null) {
                            locationPad = getModTitle(res, xml.getAttributeValue(APP_NS, "myLocationPad"));
                            locationPadId = getModId(xml.getAttributeValue(APP_NS, "myLocationPad"));
                        }
                        if (location == null) {
                            location = getModTitle(res, xml.getAttributeValue(APP_NS, "myLocation"));
                            locationId = getModId(xml.getAttributeValue(APP_NS, "myLocation"));
                        }
                        if (!TextUtils.isEmpty(modData.title) && !isPreferenceVisible) {
                            String internalHyper = internalName == null ? locationHyper : internalName + "/" + locationHyper;
                            String internalPad = internalName == null ? locationPad : internalName + "/" + locationPad;
                            String internalMiui = internalName == null ? location : internalName + "/" + location;
                            if (locationHyper == null || location == null || (isPad && locationPad == null)) {
                                if (location != null) {
                                    modData.breadcrumbs = internalMiui;
                                    modData.catTitleResId = locationId;
                                } else if (locationHyper != null) {
                                    modData.breadcrumbs = internalHyper;
                                    modData.catTitleResId = locationHyperId;
                                } else if (locationPad != null) {
                                    modData.breadcrumbs = internalPad;
                                    modData.catTitleResId = locationPadId;
                                }
                            } else {
                                if (!isPad) {
                                    if (isMoreHyperOSVersion(1f)) {
                                        modData.breadcrumbs = internalHyper;
                                        modData.catTitleResId = locationHyperId;
                                    } else {
                                        modData.breadcrumbs = internalMiui;
                                        modData.catTitleResId = locationId;
                                    }
                                } else {
                                    modData.breadcrumbs = internalPad;
                                    modData.catTitleResId = locationPadId;
                                }
                            }
                            modData.key = xml.getAttributeValue(ANDROID_NS, "key");
                            modData.order = order;

                            modData.fragment = catPrefsFragment;
                            allModsList.add(modData);
                        }
                        order++;
                    } catch (Throwable t) {
                        AndroidLogUtils.logE(TAG, "Failed to get xml keyword object!", t);
                    }
                }
                eventType = xml.next();
            }
        } catch (Throwable t) {
            AndroidLogUtils.logE(TAG, "Failed to access XML resource!", t);
        }
    }

    private static int getModId(String title) {
        if (title == null) {
            return -1;
        }
        int titleResId = Integer.parseInt(title.substring(1));
        if (titleResId <= 0) {
            return -1;
        }
        return titleResId;
    }

    private static String getModTitle(Resources res, String title) {
        int id = getModId(title);
        if (id == -1) return null;
        return res.getString(id);
    }
}
