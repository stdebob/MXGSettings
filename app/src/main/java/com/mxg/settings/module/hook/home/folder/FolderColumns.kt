// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.folder

import android.view.ViewGroup
import android.widget.GridView
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.hookAfterAllMethods
import de.robv.android.xposed.XposedHelpers

object FolderColumns : BaseHook() {
    override fun init() {

        val value = mPrefsMap.getInt("home_folder_columns", 3)
        if (value == 3) return
        "com.miui.home.launcher.Folder".findClass().hookAfterAllMethods(
            "bind"
        ) {
            val columns: Int = value
            val mContent = XposedHelpers.getObjectField(it.thisObject, "mContent") as GridView
            mContent.numColumns = columns
            if (mPrefsMap.getBoolean("home_folder_width") && (columns > 3)) {
                mContent.setPadding(0, 0, 0, 0)
                val lp = mContent.layoutParams
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                mContent.layoutParams = lp
            }
            if (columns > 3) {
                val mBackgroundView = XposedHelpers.getObjectField(it.thisObject, "mBackgroundView") as ViewGroup
                mBackgroundView.setPadding(
                    mBackgroundView.paddingLeft / 3,
                    mBackgroundView.paddingTop,
                    mBackgroundView.paddingRight / 3,
                    mBackgroundView.paddingBottom
                )
            }
        }

    }
}
