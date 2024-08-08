// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base;

import com.hchen.hooktool.HCHook;
import com.hchen.hooktool.tool.ClassTool;
import com.hchen.hooktool.tool.ExpandTool;
import com.hchen.hooktool.tool.FieldTool;
import com.hchen.hooktool.tool.MethodTool;

public abstract class BaseTool extends BaseHook {
    public HCHook hcHook;
    public ClassTool classTool;
    public MethodTool methodTool;
    public FieldTool fieldTool;
    public ExpandTool expandTool;

    public abstract void doHook();

    @Override
    public void init() {
        hcHook = new HCHook();
        classTool = hcHook.classTool();
        methodTool = hcHook.methodTool();
        fieldTool = hcHook.fieldTool();
        expandTool = hcHook.expandTool();
        hcHook.setThisTag(TAG);
        doHook();
    }
}
