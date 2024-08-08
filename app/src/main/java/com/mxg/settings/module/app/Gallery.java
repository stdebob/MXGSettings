// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.gallery.ChangeBackupServer;
import com.mxg.settings.module.hook.gallery.EnableHdrEnhance;
import com.mxg.settings.module.hook.gallery.EnableIdPhoto;
import com.mxg.settings.module.hook.gallery.EnableMagicSky;
import com.mxg.settings.module.hook.gallery.EnableOcr;
import com.mxg.settings.module.hook.gallery.EnableOcrForm;
import com.mxg.settings.module.hook.gallery.EnablePdf;
import com.mxg.settings.module.hook.gallery.EnablePhotoMovie;
import com.mxg.settings.module.hook.gallery.EnableRemover2;
import com.mxg.settings.module.hook.gallery.EnableTextYanhua;
import com.mxg.settings.module.hook.gallery.EnableVideoPost;
import com.mxg.settings.module.hook.gallery.UnPrivacyWatermark;
import com.mxg.settings.module.hook.gallery.UnlockAIGallery;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.miui.gallery", isPad = false, tarAndroid = 33)
public class Gallery extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnPrivacyWatermark(), mPrefsMap.getBoolean("gallery_enable_un_privacy_watermark"));
        initHook(new EnableHdrEnhance(), mPrefsMap.getBoolean("gallery_enable_hdr_enhanced"));
        initHook(new EnableMagicSky(), mPrefsMap.getBoolean("gallery_enable_magic_sky"));
        initHook(new EnablePdf(), mPrefsMap.getBoolean("gallery_enable_pdf"));
        initHook(new EnablePhotoMovie(), mPrefsMap.getBoolean("gallery_enable_photo_movie"));
        initHook(new EnableRemover2(), mPrefsMap.getBoolean("gallery_enable_remover_2"));
        initHook(new EnableTextYanhua(), mPrefsMap.getBoolean("gallery_enable_text_yanhua"));
        initHook(new EnableIdPhoto(), mPrefsMap.getBoolean("gallery_enable_id_photo"));
        initHook(new EnableIdPhoto(), mPrefsMap.getBoolean("gallery_enable_magic_matting"));
        initHook(new EnableVideoPost(), mPrefsMap.getBoolean("gallery_enable_video_post"));
        initHook(new EnableVideoPost(), mPrefsMap.getBoolean("gallery_enable_video_editor"));
        initHook(new EnableOcr(), mPrefsMap.getBoolean("gallery_enable_ocr"));
        initHook(new EnableOcrForm(), mPrefsMap.getBoolean("gallery_enable_ocr_form"));
        initHook(new ChangeBackupServer(), mPrefsMap.getStringAsInt("gallery_backup_server", 0) != 0);
        initHook(UnlockAIGallery.INSTANCE, mPrefsMap.getBoolean("gallery_enable_ai_gallery"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
