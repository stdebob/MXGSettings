// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.Switch;

import com.mxg.settings.R;
import com.mxg.settings.utils.TileUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class ReduceBrightColorsTile extends TileUtils {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public Class<?> customClass() {
        return findClassIfExists("com.android.systemui.qs.tiles.ReduceBrightColorsTile");
    }

    @Override
    public String customName() {
        return super.customName();
    }

    @Override
    public ArrayMap<String, Integer> tileUpdateState(XC_MethodHook.MethodHookParam param, Class<?> mResourceIcon, String tileName) {
        Context mContext = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
        int i;
        int i2;
        Object booleanState = param.args[0];
        /*boolean isReduceBrightColorsActivated = (boolean) XposedHelpers.callMethod(
            XposedHelpers.getObjectField(param.thisObject, "mReduceBrightColorsController")
            , "isReduceBrightColorsActivated"
        );*/
        boolean isReduceBrightColorsActivated = (boolean) XposedHelpers.callMethod(
            XposedHelpers.getObjectField(
                XposedHelpers.getObjectField(
                    param.thisObject,
                    "mReduceBrightColorsController"),
                "mManager"
            ), "isReduceBrightColorsActivated");
        XposedHelpers.setObjectField(booleanState, "value", isReduceBrightColorsActivated);
        if (isReduceBrightColorsActivated) {
            i = 2;
        } else i = 1;
        XposedHelpers.setObjectField(booleanState, "state", i);
        XposedHelpers.setObjectField(booleanState, "label", mContext.getString(

            R.string.system_control_center_reduce_bright_colors_tile));
        XposedHelpers.setObjectField(booleanState, "expandedAccessibilityClassName", Switch.class.getName());
        XposedHelpers.setObjectField(booleanState, "contentDescription", XposedHelpers.getObjectField(booleanState, "label"));
        if ((boolean) XposedHelpers.getObjectField(booleanState, "value")) {
            i2 = R.drawable.ic_reduce_bright_colors;
        } else
            i2 = R.drawable.ic_reduce_bright_colors;
        XposedHelpers.setObjectField(booleanState, "icon", XposedHelpers.callStaticMethod(mResourceIcon, "get", i2));
        param.setResult(null);
        return null;
    }
}
