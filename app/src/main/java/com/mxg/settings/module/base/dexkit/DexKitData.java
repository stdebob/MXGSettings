// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base.dexkit;

import java.util.ArrayList;

/**
 * dexkit 数据转为 JSON 储存
 */
public class DexKitData {
    public static final String EMPTY = "";
    public static final ArrayList<String> EMPTYLIST = new ArrayList<>();
    // public String label;
    public final String tag;
    public final String type;
    public final String clazz;
    public final String method;
    public final ArrayList<String> param;
    public final String field;

    public DexKitData(String tag, String type, String clazz,
                      String method, ArrayList<String> param,
                      String field) {
        // label = l;
        this.tag = tag;
        this.type = type;
        this.clazz = clazz;
        this.method = method;
        this.param = param;
        this.field = field;
    }
}
