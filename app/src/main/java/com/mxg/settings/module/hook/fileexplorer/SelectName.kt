// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.fileexplorer

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectField

object SelectName : BaseHook() {
    override fun init() {
        loadClass("com.android.fileexplorer.view.FileListItem").methodFinder()
            .filterByName("onFinishInflate")
            .single().createHook {
            after {
                (it.thisObject.getObjectField("mFileNameTextView") as TextView).apply {
                    setTextIsSelectable(mPrefsMap.getBoolean("file_explorer_can_selectable"))
                    isSingleLine = mPrefsMap.getBoolean("file_explorer_is_single_line")
                }
            }
        }
    }
}
