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
package com.mxg.settings.module.hook.systemui.controlcenter;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import android.content.Context;
import android.os.PowerManager;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class MuteVisibleNotifications extends BaseHook {
    String NotificationLoadClass;

    @Override
    public void init() {
        if (isMoreAndroidVersion(34)) {
            NotificationLoadClass = "com.android.systemui.statusbar.notification.policy.MiuiAlertManager";
        } else {
            NotificationLoadClass = "com.android.systemui.statusbar.notification.policy.NotificationAlertController";
        }
        hookAllMethods(NotificationLoadClass, lpparam.classLoader, "buzzBeepBlink", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    Context mContext = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
                    PowerManager powerMgr = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                    if (powerMgr.isInteractive()) {
                        param.setResult(null);
                    }
                }
            }
        );
    }
}
