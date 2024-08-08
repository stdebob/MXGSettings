// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.drawer

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethodAs
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.getObjectFieldAs
import com.mxg.settings.utils.hookAfterMethod

object AppDrawer : BaseHook() {
    override fun init() {
        if (mPrefsMap.getBoolean("home_drawer_all")) {
            try {
                loadClassOrNull("com.miui.home.launcher.allapps.category.BaseAllAppsCategoryListContainer")!!
                    .methodFinder()
                    .filterByName("buildSortCategoryList")
                    .single()
            } catch (e: Exception) {
                loadClassOrNull("com.miui.home.launcher.allapps.category.AllAppsCategoryListContainer")!!
                    .methodFinder()
                    .filterByName("buildSortCategoryList")
                    .single()
            }.createHook {
                after {
                    val list = it.result as ArrayList<*>
                    if (list.size > 1) {
                        list.removeAt(0)
                        it.result = list
                    }
                }
            }
        }

        if (mPrefsMap.getBoolean("home_drawer_editor")) {
            "com.miui.home.launcher.allapps.AllAppsGridAdapter".hookAfterMethod(
                "onBindViewHolder",
                "com.miui.home.launcher.allapps.AllAppsGridAdapter.ViewHolder".findClass(),
                Int::class.javaPrimitiveType
            ) {
                if (it.args[0].callMethodAs<Int>("getItemViewType") == 64) {
                    it.args[0].getObjectFieldAs<View>("itemView").visibility = View.INVISIBLE
                }
            }
        }

    }
}
