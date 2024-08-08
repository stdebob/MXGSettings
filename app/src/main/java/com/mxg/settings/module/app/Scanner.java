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
