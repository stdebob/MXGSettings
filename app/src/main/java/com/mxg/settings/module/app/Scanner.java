// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.scanner.EnableCard;
import com.mxg.settings.module.hook.scanner.EnableDocPpt;
import com.mxg.settings.module.hook.scanner.EnableOcr;
import com.mxg.settings.module.hook.scanner.EnableTranslation;
import com.mxg.settings.module.hook.scanner.document.EnableDocument;
import com.mxg.settings.module.hook.scanner.document.EnableExcel;
import com.mxg.settings.module.hook.scanner.document.EnablePpt;

@HookExpand(pkg = "com.xiaomi.scanner", isPad = false, tarAndroid = 33)
public class Scanner extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new EnableOcr(), mPrefsMap.getBoolean("scanner_ocr"));
        initHook(new EnableExcel(), mPrefsMap.getBoolean("scanner_excel"));
        initHook(new EnablePpt(), mPrefsMap.getBoolean("scanner_ppt"));
        initHook(new EnableCard(), mPrefsMap.getBoolean("scanner_card"));
        initHook(new EnableTranslation(), mPrefsMap.getBoolean("scanner_translation"));
        initHook(new EnableDocument(), mPrefsMap.getBoolean("scanner_document"));
        initHook(new EnableDocPpt(), mPrefsMap.getBoolean("scanner_doc_ppt"));
    }
}
