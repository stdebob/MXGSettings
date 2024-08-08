// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.callback;

/**
 * Shell 工具的回调方法。
 *
 * @author 焕晨HChen
 */
public interface IResult {
    /**
     * 重写本方法可以实时获取常规流数据。
     *
     * @param out 常规流数据
     */
    default void readOutput(String out, boolean finish) {
    }

    /**
     * 重写本方法可以实时获取错误流数据。
     *
     * @param out 错误流数据
     */
    default void readError(String out) {
    }

    /**
     * 重写本方法可以实时获取每条命令的执行结果。
     *
     * @param command 命令
     * @param result  结果
     */
    default void result(String command, int result) {
    }

    /**
     * 无 Root 权限时的报错回调。
     *
     * @param reason 原因
     */
    default void error(String reason) {
    }
}
