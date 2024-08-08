// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base.dexkit;

import org.luckypray.dexkit.DexKitBridge;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

public interface IDexKitList {
    List<AnnotatedElement> dexkit(DexKitBridge bridge)
            throws ReflectiveOperationException;
}
