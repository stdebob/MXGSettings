// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.folder

import android.widget.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*
import com.mxg.settings.utils.devicesdk.DisplayUtils.*
import de.robv.android.xposed.*

object FolderVerticalPadding : BaseHook() {
    override fun init() {

        val verticalPadding = mPrefsMap.getInt("home_folder_vertical_padding", 0)
        if (verticalPadding <= 0) return
        "com.miui.home.launcher.Folder".findClass().hookAfterAllMethods(
            "bind"
        ) {
            val mContent = XposedHelpers.getObjectField(it.thisObject, "mContent") as GridView
            mContent.verticalSpacing = dp2px(verticalPadding.toFloat())
        }

    }
}
