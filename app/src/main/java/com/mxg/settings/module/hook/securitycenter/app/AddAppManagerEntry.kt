// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.initAppContext
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.R
import com.mxg.settings.module.base.BaseHook

@SuppressLint("DiscouragedApi")
object AddAppManagerEntry : BaseHook() {
    //override val key = "add_aosp_app_manager_entry"
    private val idIdMiuixActionEndMenuGroup by lazy {
        appContext.resources.getIdentifier("miuix_action_end_menu_group", "id", hostPackageName)
    }
    private val idDrawableIconSettings by lazy {
        appContext.resources.getIdentifier("icon_settings", "drawable", hostPackageName)
    }

    override fun init() {
        val clazzAppManagerMainActivity = loadClass("com.miui.appmanager.AppManagerMainActivity")
        clazzAppManagerMainActivity.methodFinder().filterByName("onCreateOptionsMenu").first()
            .createHook {
                after {
                    initAppContext(it.thisObject as Activity, true)
                    val menuItem = (it.args[0] as Menu).add(
                        idIdMiuixActionEndMenuGroup, 0, 0, R.string.security_center_aosp_app_manager
                    )
                    menuItem.intent = Intent(Intent.ACTION_MAIN).setClassName(
                        "com.android.settings",
                        "com.android.settings.applications.ManageApplications"
                    )
                    menuItem.setIcon(idDrawableIconSettings)
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }
            }
    }
}
