// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.externalstorage;

import static com.mxg.settings.module.base.tool.HookTool.MethodHook.returnConstant;

import com.mxg.settings.module.base.BaseHook;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisableFolderCantUse extends BaseHook {

    private final static List<String> METHOD_NAME_LIST = Arrays.asList("shouldBlockFromTree", "shouldBlockDirectoryFromTree");

    @Override
    public void init() {
        Class<?> externalStorageProvider = findClass("com.android.externalstorage.ExternalStorageProvider");
        List<Method> methodList = Arrays.stream(externalStorageProvider.getDeclaredMethods())
                .filter(method -> METHOD_NAME_LIST.contains(method.getName()))
                .filter(method -> method.getReturnType() == boolean.class).collect(Collectors.toList());

        if (methodList.isEmpty()) {
            logE(TAG, lpparam.packageName, new NoSuchMethodException("shouldBlockFromTree"));
            return;
        }

        for (Method method : methodList) {
            hookMethod(method, returnConstant(false));
        }
    }
}
