// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.folder;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.blur.BlurUtils;
import com.mxg.settings.utils.devicesdk.DisplayUtils;

import de.robv.android.xposed.XposedHelpers;

public class BigFolderIconBlur1x2 extends BaseHook {

    Class<?> mFolderIcon1x2;

    boolean isShowEditPanel;
    Class<?> mLauncher;
    Class<?> mFolderInfo;
    Class<?> mFolderIcon;
    Class<?> mLauncherState;
    Class<?> mDragView;

    @Override
    public void init() {
        if (isPad()) {
            mFolderIcon1x2 = findClassIfExists("com.miui.home.launcher.folder.FolderIcon2x2_4");
        } else {
            mFolderIcon1x2 = findClassIfExists("com.miui.home.launcher.folder.FolderIcon1x2");
        }

        mLauncher = findClassIfExists("com.miui.home.launcher.Launcher");
        mFolderInfo = findClassIfExists("com.miui.home.launcher.FolderInfo");
        mFolderIcon = findClassIfExists("com.miui.home.launcher.FolderIcon");
        mLauncherState = findClassIfExists("com.miui.home.launcher.LauncherState");
        mDragView = findClassIfExists("com.miui.home.launcher.DragView");

        MethodHook mBigFolderIconBlur = new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                int mFolderWidth = DisplayUtils.dp2px(mPrefsMap.getInt("home_big_folder_icon_bg_width_1x2", 62));
                int mFolderHeight = DisplayUtils.dp2px(mPrefsMap.getInt("home_big_folder_icon_bg_height_1x2", 145));
                ImageView mIconImageView = (ImageView) XposedHelpers.getObjectField(param.thisObject, "mIconImageView");
                FrameLayout mIconContainer = (FrameLayout) mIconImageView.getParent();
                FrameLayout mDockBlur = (FrameLayout) XposedHelpers.getAdditionalInstanceField(param.thisObject, "mDockBlur");
                FrameLayout view = new FrameLayout(mIconImageView.getContext());

                mIconImageView.setVisibility(View.GONE);
                mDockBlur.addView(view);
                new BlurUtils(mDockBlur, "home_big_folder_icon_bg_1x2_custom");

                mIconContainer.addView(mDockBlur, 0);
                FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) mDockBlur.getLayoutParams();
                lp1.gravity = Gravity.CENTER;
                lp1.height = mFolderHeight;
                lp1.width = mFolderWidth;
                findAndHookMethod(mLauncher, "showEditPanel", boolean.class, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        isShowEditPanel = (boolean) param.args[0];
                        if (isShowEditPanel) {
                            mDockBlur.setVisibility(View.GONE);
                            mIconImageView.setVisibility(View.VISIBLE);
                        } else {
                            mDockBlur.setVisibility(View.VISIBLE);
                            mIconImageView.setVisibility(View.GONE);
                        }
                    }
                });

                findAndHookMethod(mLauncher, "openFolder", mFolderInfo, View.class, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        mDockBlur.setVisibility(View.GONE);
                    }
                });

                findAndHookMethod(mLauncher, "closeFolder", boolean.class, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        if (!isShowEditPanel) mDockBlur.setVisibility(View.VISIBLE);
                    }
                });

                findAndHookMethod(mLauncher, "onStateSetStart", mLauncherState, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        Boolean mLauncherState = param.args[0].getClass().getSimpleName().equals("LauncherState");
                        Boolean mNormalState = param.args[0].getClass().getSimpleName().equals("NormalState");

                        if (mLauncherState || mNormalState) {
                            mDockBlur.setVisibility(View.VISIBLE);
                        } else {
                            mDockBlur.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };

        findAndHookMethod(mFolderIcon1x2, "onFinishInflate", mBigFolderIconBlur);
    }
}
