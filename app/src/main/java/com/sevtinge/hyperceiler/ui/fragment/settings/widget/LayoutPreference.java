package com.sevtinge.hyperceiler.ui.fragment.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.core.content.res.TypedArrayUtils;


import com.sevtinge.hyperceiler.R;

import fan.preference.FolmeAnimationController;
import fan.preference.Preference;
import fan.preference.PreferenceViewHolder;

/**
 * A preference can be simply customized a view by adding layout attribute in xml.
 * User also can decide whether or not LayoutPreference allows above divider or below divider.
 *
 * For instances,
 *
 * <com.android.settingslib.widget.LayoutPreference
 *      ...
 *      android:layout="@layout/settings_entity_header"
 *      xxxxxxx:allowDividerAbove="true"
 *      xxxxxxx:allowDividerBelow="true"
 *
 */
public class LayoutPreference extends Preference implements FolmeAnimationController {

    private final View.OnClickListener mClickListener = v -> performClick(v);
    private boolean mAllowDividerAbove;
    private boolean mAllowDividerBelow;
    private View mRootView;

    @Override
    public boolean isTouchAnimationEnable() {
        return false;
    }


    /**
     * Constructs a new LayoutPreference with the given context's theme and the supplied
     * attribute set.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     */
    public LayoutPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0 /* defStyleAttr */);
    }

    /**
     * Constructs a new LayoutPreference with the given context's theme, the supplied
     * attribute set, and default style attribute.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default
     *                     values for the view. Can be 0 to not look for
     *                     defaults.
     */
    public LayoutPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Constructs a new LayoutPreference with the given context's theme and a customized view id.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param resource The view id which you expected to be inflated and show in preference.
     */
    public LayoutPreference(Context context, int resource) {
        this(context, LayoutInflater.from(context).inflate(resource, null, false));
    }

    /**
     * Constructs a new LayoutPreference with the given context's theme and a customized view.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param view The view which you expected show in preference.
     */
    public LayoutPreference(Context context, View view) {
        super(context);
        setView(view);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, fan.preference.R.styleable.Preference);
        mAllowDividerAbove = TypedArrayUtils.getBoolean(a, fan.preference.R.styleable.Preference_allowDividerAbove,
                fan.preference.R.styleable.Preference_allowDividerAbove, false);
        mAllowDividerBelow = TypedArrayUtils.getBoolean(a, fan.preference.R.styleable.Preference_allowDividerBelow,
                fan.preference.R.styleable.Preference_allowDividerBelow, false);
        a.recycle();

        a = context.obtainStyledAttributes(
                attrs, fan.preference.R.styleable.Preference, defStyleAttr, 0);
        int layoutResource = a.getResourceId(fan.preference.R.styleable.Preference_android_layout, 0);
        if (layoutResource == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        a.recycle();

        // Need to create view now so that findViewById can be called immediately.
        final View view = LayoutInflater.from(getContext())
                .inflate(layoutResource, null, false);
        setView(view);
    }

    private void setView(View view) {
        setLayoutResource(R.layout.preference_layout_frame);
        mRootView = view;
        setShouldDisableView(false);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        holder.itemView.setOnClickListener(mClickListener);

        final boolean selectable = isSelectable();
        holder.itemView.setFocusable(selectable);
        holder.itemView.setClickable(selectable);
        holder.setDividerAllowedAbove(mAllowDividerAbove);
        holder.setDividerAllowedBelow(mAllowDividerBelow);

        FrameLayout layout = (FrameLayout) holder.itemView;
        layout.removeAllViews();
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        layout.addView(mRootView);
    }

    /**
     * Finds the view with the given ID.
     *
     * @param id the ID to search for
     * @return a view with given ID if found, or {@code null} otherwise
     */
    public <T extends View> T findViewById(int id) {
        return mRootView.findViewById(id);
    }

    /**
     * LayoutPreference whether or not allows to set a below divider.
     */
    public void setAllowDividerBelow(boolean allowed) {
        mAllowDividerBelow = allowed;
    }

    /**
     * Return a value whether or not LayoutPreference allows to set a below divider.
     */
    public boolean isAllowDividerBelow() {
        return mAllowDividerBelow;
    }

}
