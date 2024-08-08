// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.callback;

public interface IAppSelectCallback {

    void sendMsgToActivity(byte[] appIcon, String appName, String appPackageName, String appVersion, String appActivityName);

    String getMsgFromActivity(String s);
}
