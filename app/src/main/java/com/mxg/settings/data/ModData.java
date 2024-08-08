// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.data;

public class ModData {
    public String title;
    public String breadcrumbs;
    public String key;
    public ModCat cat;
    public String sub;
    public int order;
    public String fragment;
    public int catTitleResId;

    public enum ModCat {
        pref_key_system,
        pref_key_launcher,
        pref_key_controls,
        pref_key_various
    }
}
