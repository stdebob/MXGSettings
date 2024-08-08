// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.camera.BlackLeica;
import com.mxg.settings.module.hook.camera.CustomCameraColor;
import com.mxg.settings.module.hook.camera.CustomWatermark;
import com.mxg.settings.module.hook.camera.EnableLabOptions;
import com.mxg.settings.module.hook.camera.MaxScreenBrightness;
import com.mxg.settings.module.hook.camera.Unlock120Fps;
import com.mxg.settings.module.hook.camera.Unlock60Fps;
import com.mxg.settings.module.hook.camera.UnlockAiEnhance;
import com.mxg.settings.module.hook.camera.UnlockAiShutter;
import com.mxg.settings.module.hook.camera.UnlockAiWatermark;
import com.mxg.settings.module.hook.camera.UnlockAmbilight;
import com.mxg.settings.module.hook.camera.UnlockAudioZoom;
import com.mxg.settings.module.hook.camera.UnlockClone;
import com.mxg.settings.module.hook.camera.UnlockColorEnhance;
import com.mxg.settings.module.hook.camera.UnlockCosmeticMirror;
import com.mxg.settings.module.hook.camera.UnlockCvlens;
import com.mxg.settings.module.hook.camera.UnlockCyberFocusVersion2;
import com.mxg.settings.module.hook.camera.UnlockDoc;
import com.mxg.settings.module.hook.camera.UnlockDualcam;
import com.mxg.settings.module.hook.camera.UnlockDuration;
import com.mxg.settings.module.hook.camera.UnlockFilm;
import com.mxg.settings.module.hook.camera.UnlockFilmDelay;
import com.mxg.settings.module.hook.camera.UnlockFilmDollyZoom;
import com.mxg.settings.module.hook.camera.UnlockFilmDream;
import com.mxg.settings.module.hook.camera.UnlockFilmSlowShutter;
import com.mxg.settings.module.hook.camera.UnlockFilmTimeBackflow;
import com.mxg.settings.module.hook.camera.UnlockFilmTimeFreeze;
import com.mxg.settings.module.hook.camera.UnlockHandGesture;
import com.mxg.settings.module.hook.camera.UnlockHdr;
import com.mxg.settings.module.hook.camera.UnlockHeic;
import com.mxg.settings.module.hook.camera.UnlockIdcard;
import com.mxg.settings.module.hook.camera.UnlockLog;
import com.mxg.settings.module.hook.camera.UnlockMakeup;
import com.mxg.settings.module.hook.camera.UnlockMenMakeup;
import com.mxg.settings.module.hook.camera.UnlockMilive;
import com.mxg.settings.module.hook.camera.UnlockMoon;
import com.mxg.settings.module.hook.camera.UnlockNevus;
import com.mxg.settings.module.hook.camera.UnlockNewBeauty;
import com.mxg.settings.module.hook.camera.UnlockPano;
import com.mxg.settings.module.hook.camera.UnlockPixel;
import com.mxg.settings.module.hook.camera.UnlockPortrait;
import com.mxg.settings.module.hook.camera.UnlockRaw;
import com.mxg.settings.module.hook.camera.UnlockSlow;
import com.mxg.settings.module.hook.camera.UnlockTrackEyes;
import com.mxg.settings.module.hook.camera.UnlockTrackFeature;
import com.mxg.settings.module.hook.camera.UnlockTrackFocus;
import com.mxg.settings.module.hook.camera.UnlockVideosky;
import com.mxg.settings.module.hook.camera.UnlockVlog;
import com.mxg.settings.module.hook.camera.UnlockVlogPro;

@HookExpand(pkg = "com.android.camera", isPad = false, tarAndroid = 33)
public class Camera extends BaseModule {

    @Override
    public void handleLoadPackage() {
        // 功能
        initHook(new UnlockAiWatermark(), mPrefsMap.getBoolean("camera_features_unlock_aiwatermark"));
        initHook(new UnlockAmbilight(), mPrefsMap.getBoolean("camera_features_unlock_ambilight"));
        initHook(new UnlockClone(), mPrefsMap.getBoolean("camera_features_unlock_clone"));
        initHook(new UnlockCosmeticMirror(), mPrefsMap.getBoolean("camera_features_unlock_cosmetic_mirror"));
        initHook(new UnlockDoc(), mPrefsMap.getBoolean("camera_features_unlock_doc"));
        initHook(new UnlockDualcam(), mPrefsMap.getBoolean("camera_features_unlock_dualcam"));
        initHook(new UnlockDuration(), mPrefsMap.getBoolean("camera_features_unlock_duration"));
        initHook(new UnlockIdcard(), mPrefsMap.getBoolean("camera_features_unlock_idcard"));
        initHook(new UnlockMilive(), mPrefsMap.getBoolean("camera_features_unlock_milive"));
        initHook(new UnlockMoon(), mPrefsMap.getBoolean("camera_features_unlock_moon"));
        initHook(new UnlockPano(), mPrefsMap.getBoolean("camera_features_unlock_pano"));
        initHook(new UnlockPixel(), mPrefsMap.getBoolean("camera_features_unlock_pixel"));
        initHook(new UnlockPortrait(), mPrefsMap.getBoolean("camera_features_unlock_portrait"));
        initHook(new UnlockSlow(), mPrefsMap.getBoolean("camera_features_unlock_slow"));
        initHook(new UnlockVideosky(), mPrefsMap.getBoolean("camera_features_unlock_videosky"));
        initHook(new UnlockVlog(), mPrefsMap.getBoolean("camera_features_unlock_vlog"));
        initHook(new UnlockVlogPro(), mPrefsMap.getBoolean("camera_features_unlock_vlog_pro"));
        initHook(new UnlockFilm(), mPrefsMap.getBoolean("camera_features_unlock_film"));
        initHook(new UnlockFilmDelay(), mPrefsMap.getBoolean("camera_features_unlock_film_delay"));
        initHook(new UnlockFilmDollyZoom(), mPrefsMap.getBoolean("camera_features_unlock_film_dollyzoom"));
        initHook(new UnlockFilmDream(), mPrefsMap.getBoolean("camera_features_unlock_film_dream"));
        initHook(new UnlockFilmSlowShutter(), mPrefsMap.getBoolean("camera_features_unlock_film_slowshutter"));
        initHook(new UnlockFilmTimeBackflow(), mPrefsMap.getBoolean("camera_features_unlock_film_timebackflow"));
        initHook(new UnlockFilmTimeFreeze(), mPrefsMap.getBoolean("camera_features_unlock_film_timefreeze"));

        // 拍照
        initHook(new UnlockMakeup(), mPrefsMap.getBoolean("camera_shot_makeup"));
        initHook(new UnlockColorEnhance(), mPrefsMap.getBoolean("camera_shot_color_enhance"));
        initHook(new UnlockHandGesture(), mPrefsMap.getBoolean("camera_shot_hand_gesture"));
        initHook(new CustomWatermark(), mPrefsMap.getBoolean("camera_custom_watermark"));
        initHook(new BlackLeica(), mPrefsMap.getBoolean("camera_black_leica"));

        // 录像
        initHook(new Unlock60Fps(), mPrefsMap.getBoolean("camera_record_60fps"));
        initHook(new Unlock120Fps(), mPrefsMap.getBoolean("camera_record_120fps"));
        initHook(new UnlockHdr(), mPrefsMap.getBoolean("camera_record_hdr"));
        initHook(new UnlockAiEnhance(), mPrefsMap.getBoolean("camera_record_ai"));
        initHook(new UnlockAudioZoom(), mPrefsMap.getBoolean("camera_record_audio_zoom"));

        // 人像
        initHook(new UnlockCvlens(), mPrefsMap.getBoolean("camera_portrait_cvlens"));
        initHook(new UnlockNewBeauty(), mPrefsMap.getBoolean("camera_portrait_new_beauty"));

        // 专业
        initHook(new UnlockRaw(), mPrefsMap.getBoolean("camera_pro_raw"));
        initHook(new UnlockLog(), mPrefsMap.getBoolean("camera_pro_log"));

        // 设置
        initHook(new UnlockTrackFeature(), (mPrefsMap.getBoolean("camera_settings_track_eyes") || mPrefsMap.getBoolean("camera_settings_track_focus")));
        initHook(new UnlockHeic(), mPrefsMap.getBoolean("camera_settings_heic"));
        initHook(new UnlockTrackFocus(), mPrefsMap.getBoolean("camera_settings_track_focus"));
        initHook(new UnlockAiShutter(), mPrefsMap.getBoolean("camera_settings_predictive"));
        initHook(new UnlockCyberFocusVersion2(), mPrefsMap.getBoolean("camera_settings_track_focus"));
        initHook(new UnlockNevus(), mPrefsMap.getBoolean("camera_settings_nevus"));
        initHook(new UnlockMenMakeup(), mPrefsMap.getBoolean("camera_settings_men_makeup"));
        initHook(new EnableLabOptions(), mPrefsMap.getBoolean("camera_settings_lab_options"));
        initHook(new UnlockTrackEyes(), mPrefsMap.getBoolean("camera_settings_track_eyes"));

        initHook(new MaxScreenBrightness(), mPrefsMap.getBoolean("camera_max_brightness"));
        initHook(new CustomCameraColor(), mPrefsMap.getBoolean("camera_custom_theme_color"));
    }
}
