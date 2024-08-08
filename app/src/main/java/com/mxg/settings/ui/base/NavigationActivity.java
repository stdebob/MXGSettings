// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.base;

import static com.mxg.settings.utils.Helpers.isDarkMode;
import static com.mxg.settings.utils.devicesdk.DisplayUtils.dp2px;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mxg.settings.R;
import com.mxg.settings.data.ModData;
import com.mxg.settings.data.adapter.ModSearchAdapter;
import com.mxg.settings.data.adapter.NavigationPagerAdapter;
import com.mxg.settings.ui.SubSettings;
import com.mxg.settings.ui.fragment.MainFragment;
import com.mxg.settings.ui.fragment.base.AboutFragment;
import com.mxg.settings.ui.fragment.base.settings.ModuleSettingsFragment;
import com.mxg.settings.utils.DialogHelper;
import com.mxg.settings.utils.SettingLauncherHelper;
import com.mxg.settings.utils.blur.MiBlurUtils;
import com.mxg.settings.utils.search.SearchModeHelper;

import java.util.ArrayList;

import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceFragmentCompat;
import moralnorm.view.SearchActionMode;

public abstract class NavigationActivity extends BaseActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    String lastFilter;
    View mSearchView;
    TextView mSearchInputView;
    RecyclerView mSearchResultView;
    ModSearchAdapter mSearchAdapter;

    ViewPager mFragmentPage;
    RadioGroup mNavigationView;
    RadioButton mHomeNav, mSettingsNav, mAboutNav;
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    NavigationPagerAdapter mNavigationPagerAdapter;
    MainFragment mainFragment = new MainFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initSearchView();
        initNavigationView();
        setRestartView(view -> DialogHelper.showRestartDialog(this));
    }

    private void initSearchView() {
        mSearchView = findViewById(R.id.search_view);
        mSearchInputView = findViewById(android.R.id.input);
        mSearchResultView = findViewById(R.id.search_result_view);
        mSearchAdapter = new ModSearchAdapter();
        mSearchInputView.setHint(getResources().getString(R.string.search));
        mSearchResultView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultView.setAdapter(mSearchAdapter);

        mSearchView.setOnClickListener(v -> startSearchMode());
        mSearchAdapter.setOnItemClickListener((view, ad) -> onSearchItemClickListener(ad));

        ViewCompat.setOnApplyWindowInsetsListener(mSearchResultView, new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets inset = Insets.max(insets.getInsets(WindowInsetsCompat.Type.systemBars()),
                        insets.getInsets(WindowInsetsCompat.Type.displayCutout()));
                v.setPadding(0, 0, 0, inset.bottom);
                return insets;
            }
        });
    }

    private void initNavigationView() {
        mFragmentPage = findViewById(R.id.frame_page);
        mNavigationView = findViewById(R.id.navigation);
        mHomeNav = findViewById(R.id.navigation_home);
        mSettingsNav = findViewById(R.id.navigation_settings);
        mAboutNav = findViewById(R.id.navigation_about);

        mFragmentList.add(mainFragment);
        mFragmentList.add(new ModuleSettingsFragment());
        mFragmentList.add(new AboutFragment());
        mNavigationPagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mFragmentPage.setAdapter(mNavigationPagerAdapter);

        Context context = this;
        addPaddingForRadioButton(mHomeNav, context);
        addPaddingForRadioButton(mSettingsNav, context);
        addPaddingForRadioButton(mAboutNav, context);

        int i;
        if (isDarkMode(this)) i = 160;
        else i = 200;
        int a;
        if (isDarkMode(this)) a = 100;
        else a = 140;
        MiBlurUtils.setContainerPassBlur(mNavigationView, i, true);
        MiBlurUtils.setMiViewBlurMode(mNavigationView, 3);
        MiBlurUtils.clearMiBackgroundBlendColor(mNavigationView);
        MiBlurUtils.addMiBackgroundBlendColor(mNavigationView, Color.argb(a, 0, 0, 0), 103);

        mNavigationView.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.navigation_home) {
                mFragmentPage.setCurrentItem(0);
            } else if (checkedId == R.id.navigation_settings) {
                mFragmentPage.setCurrentItem(1);
            } else if (checkedId == R.id.navigation_about) {
                mFragmentPage.setCurrentItem(2);
            }
        });

        mFragmentPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeSelect(position);
                mSearchView.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addPaddingForRadioButton(View view, Context context) {
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets inset = Insets.max(insets.getInsets(WindowInsetsCompat.Type.systemBars()),
                        insets.getInsets(WindowInsetsCompat.Type.displayCutout()));
                v.setPadding(0, dp2px(context, 10), 0, inset.bottom + dp2px(context, 14));
                return insets;
            }
        });
    }

    private void changeSelect(int position) {
        switch (position) {
            case 0 -> {
                mHomeNav.setChecked(true);
                mFragmentPage.setCurrentItem(0);
                setTitle(R.string.app_name);
            }
            case 1 -> {
                mSettingsNav.setChecked(true);
                mFragmentPage.setCurrentItem(1);
                setTitle(R.string.settings);
            }
            case 2 -> {
                mAboutNav.setChecked(true);
                mFragmentPage.setCurrentItem(2);
                setTitle(R.string.about);
            }
        }
    }

    private void onSearchItemClickListener(ModData ad) {
        Bundle args = new Bundle();
        args.putString(":settings:fragment_args_key", ad.key);
        SettingLauncherHelper.onStartSettingsForArguments(
                this,
                SubSettings.class,
                ad.fragment,
                args,
                ad.catTitleResId
        );
    }

    private SearchActionMode startSearchMode() {
        return SearchModeHelper.startSearchMode(
                this,
                mSearchResultView,
                mFragmentPage,
                mSearchView,
                findViewById(android.R.id.list_container),
                mSearchResultListener
        );
    }

    TextWatcher mSearchResultListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            findMod(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // findMod(s.toString());
        }
    };

    void findMod(String filter) {
        lastFilter = filter;
        mSearchResultView.setVisibility(filter.isEmpty() ? View.GONE : View.VISIBLE);
        ModSearchAdapter adapter = (ModSearchAdapter) mSearchResultView.getAdapter();
        if (adapter == null) return;
        adapter.getFilter(NavigationActivity.this).filter(filter);
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat preferenceFragmentCompat, @NonNull Preference preference) {
        mProxy.onStartSettingsForArguments(SubSettings.class, preference, false);
        return true;
    }
}
