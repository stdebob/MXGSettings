// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base;

public interface IXposedHook {

    void initZygote();

    void handleLoadPackage();
}
