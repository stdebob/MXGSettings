// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.prefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mxg.settings.R;
import com.mxg.settings.ui.SubSettings;
import com.mxg.settings.utils.SettingLauncherHelper;

import java.util.ArrayList;
import java.util.List;

import moralnorm.animation.Folme;
import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceViewHolder;

public class RecommendPreference extends Preference {

    private Context mContext;
    private int mTopMargin;
    private boolean mNeedClear;
    private String mRecommendTips;
    private TextView mRecommendTipsTv;
    private List<RelativeLayout> mItemList;
    private LinearLayout mLinearLayout;

    public RecommendPreference(@NonNull Context context) {
        this(context, 0, false);
    }

    public RecommendPreference(Context context, int topMargin, boolean needClear) {
        this(context, null);
        mContext = context;
        mTopMargin = topMargin;
        mNeedClear = needClear;
    }

    public RecommendPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mItemList = new ArrayList<>();
        init();
    }

    private void init() {
        setLayoutResource(R.layout.preference_recommend);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        onBindView(holder.itemView);
    }

    private void onBindView(View view) {
        Folme.clean(view);
        view.setPadding(0, 0, 0, 0);
        view.setBackgroundColor(Color.TRANSPARENT);
        mLinearLayout = view.findViewById(R.id.line_layout);
        mRecommendTipsTv = view.findViewById(R.id.recommend_tip);
        if (mRecommendTipsTv != null && !TextUtils.isEmpty(mRecommendTips)) {
            mRecommendTipsTv.setText(mRecommendTips);
        }
        if (mTopMargin > 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLinearLayout.getLayoutParams();
            params.topMargin = mTopMargin;
            mLinearLayout.setLayoutParams(params);
        }
        tryClearRecommendView();
        for (RelativeLayout parent : mItemList) {
            if (parent.getParent() == null) {
                mLinearLayout.addView(parent);
            }
        }
    }

    public void addRecommendView(String title, Intent intent, Class<?> fclazz, Bundle args, int titleResId) {
        RelativeLayout parent = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.preference_recommend_item, null);
        TextView textView = parent.findViewById(R.id.recommend_item);
        textView.setText(title);
        textView.setOnClickListener(view -> {
            String fname = fclazz.getName();
            if (intent == null) {
                startWithFragment(fname, args, titleResId);
            } else {
                mContext.startActivity(intent);
            }
        });
        mItemList.add(parent);
    }

    public void startWithFragment(String fragment, Bundle args, int titleResId) {
        SettingLauncherHelper.onStartSettingsForArguments(
                getContext(),
                SubSettings.class,
                fragment,
                args,
                titleResId
        );

        /*FragmentManager supportFragmentManager = this.mContext.getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (findFragmentByTag == null) {
            findFragmentByTag = Fragment.instantiate(this.mContext, str, bundle);
        }
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.addToBackStack(str);
        beginTransaction.replace(R.id.content, findFragmentByTag, str);
        beginTransaction.commitAllowingStateLoss();*/
    }

    private void tryClearRecommendView() {
        if (mLinearLayout != null && this.mNeedClear) {
            for (int childCount = mLinearLayout.getChildCount() - 1; childCount > 0; childCount--) {
                mLinearLayout.removeViewAt(childCount);
            }
        }
    }

    public void setRecommendTips(String tips) {
        mRecommendTips = tips;
        notifyChanged();
    }
}
