// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.view.Menu
import android.view.MenuItem
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.initAppContext
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.R
import com.mxg.settings.module.base.BaseHook

@SuppressLint("DiscouragedApi")
object AddAppInfoEntry : BaseHook() {
    //override val key = "add_aosp_app_info_entry"
    private val idIdMiuixActionEndMenuGroup by lazy {
        appContext.resources.getIdentifier("miuix_action_end_menu_group", "id", hostPackageName)
    }
    private val idDrawableIconSettings by lazy {
        appContext.resources.getIdentifier("icon_settings", "drawable", hostPackageName)
    }
    private val idStringAppManagerAppInfoLabel by lazy {
        appContext.resources.getIdentifier("app_manager_app_info_label", "string", hostPackageName)
    }

    override fun init() {
        val clazzApplicationsDetailsActivity =
            loadClass("com.miui.appmanager.ApplicationsDetailsActivity")
        clazzApplicationsDetailsActivity.methodFinder().filterByName("onCreateOptionsMenu").first()
            .createHook {
                after {
                    val activity = it.thisObject as Activity
                    initAppContext(activity, true)
                    val pkgName = activity.intent.getStringExtra("package_name")!!
                    val myUserId =
                        invokeStaticMethodBestMatch(UserHandle::class.java, "myUserId") as Int
                    val uid = activity.intent.getIntExtra("miui.intent.extra.USER_ID", myUserId)
                    val menuItem = (it.args[0] as Menu).add(
                        idIdMiuixActionEndMenuGroup, 0, 0, R.string.security_center_aosp_app_info_label
                    )
                    menuItem.intent = Intent(Intent.ACTION_MAIN).apply {
                        val bundle = Bundle().apply {
                            putString("package", pkgName)
                            putInt("uid", uid)
                        }
                        val stringAppManagerAppInfoLabel =
                            activity.getString(idStringAppManagerAppInfoLabel)
                        setClassName("com.android.settings", "com.android.settings.SubSettings")
                        putExtra(
                            ":settings:show_fragment",
                            "com.android.settings.applications.appinfo.AppInfoDashboardFragment"
                        )
                        putExtra(":settings:show_fragment_title", stringAppManagerAppInfoLabel)
                        putExtra(":settings:show_fragment_args", bundle)
                    }
                    menuItem.setIcon(idDrawableIconSettings)
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }
            }
    }
}
