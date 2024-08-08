// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class SquigglyProgress extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookConstructor("com.android.systemui.media.controls.models.player.MediaViewHolder",
                android.view.View.class,
                new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        SeekBar seekBar = (SeekBar) XposedHelpers.getObjectField(param.thisObject, "seekBar");
                        Object squigglyProgress = XposedHelpers.newInstance(
                                findClassIfExists("com.android.systemui.media.controls.ui.SquigglyProgress"));
                        seekBar.setProgressDrawable((Drawable) squigglyProgress);
                    }
                }
        );
    }
}
