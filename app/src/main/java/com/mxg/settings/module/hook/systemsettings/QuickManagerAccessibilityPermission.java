// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import static com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;

import com.mxg.settings.module.base.BaseHook;

import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class QuickManagerAccessibilityPermission extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.settings.SettingsActivity", "onCreate", android.os.Bundle.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                super.after(param);
                Activity activity = (Activity) param.thisObject;
                Intent intent = activity.getIntent();
                String action = intent.getAction();
                if (action != null && action.equals("android.settings.ACCESSIBILITY_SETTINGS")) {
                    //获取打开此Activity的应用包名
                    String packageName = (String) XposedHelpers.getObjectField(intent, "mSenderPackageName");
                    //XposedBridge.log("启动包名：" + packageName);
                    if (packageName == null) {
                        return;
                    }

                    String accessibilityService = null;
                    String summary = null;

                    PackageManager packageManager = activity.getPackageManager();
                    AccessibilityManager accessibilityManager = (AccessibilityManager) XposedHelpers.callStaticMethod(AccessibilityManager.class, "getInstance", new Class[]{Context.class}, activity);
                    //遍历无障碍服务列表，直到与之相同的包名
                    List<AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager.getInstalledAccessibilityServiceList();
                    for (AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
                        ServiceInfo serviceInfo = accessibilityServiceInfo.getResolveInfo().serviceInfo;
                        if (packageName.equals(serviceInfo.packageName)) {
                            accessibilityService = serviceInfo.name;
                            //获取简介
                            summary = accessibilityServiceInfo.loadDescription(packageManager);
                        }
                    }

                    //获取应用名称
                    String appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString();

                    if (accessibilityService == null) {
                        logE(TAG, lpparam.packageName, "Has no accessibility services.");
                        return;
                    }

                    logI(TAG, lpparam.packageName, "Accessibility services is " + accessibilityService);


                    Intent intentOpenSub = new Intent(activity, loadClassOrNull("com.android.settings.SubSettings", lpparam.classLoader));
                    intentOpenSub.setAction("android.intent.action.MAIN");
                    intentOpenSub.putExtra(":settings:show_fragment_title", appName);
                    intentOpenSub.putExtra(":settings:source_metrics", 0);
                    intentOpenSub.putExtra(":settings:show_fragment_title_resid", -1);
                    intentOpenSub.putExtra(":settings:show_fragment", "com.android.settings.accessibility.VolumeShortcutToggleAccessibilityServicePreferenceFragment");
                    Bundle bundle1 = new Bundle();
                    ComponentName componentName = new ComponentName(packageName, accessibilityService);
                    bundle1.putParcelable("component_name", componentName);
                    bundle1.putString("package", packageName);
                    bundle1.putString("preference_key", packageName + "/" + accessibilityService);
                    bundle1.putString("summary", summary);
                    intentOpenSub.putExtra(":settings:show_fragment_args", bundle1);
                    activity.startActivity(intentOpenSub);
                }
            }
        });
    }
}
