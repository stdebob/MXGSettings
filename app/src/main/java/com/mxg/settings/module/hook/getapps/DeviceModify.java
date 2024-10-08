// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.getapps;

import android.os.Build;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class DeviceModify extends BaseHook {

    String mDevice;
    String mModel;
    String mManufacturer;

    @Override
    public void init() {
        if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 106) {
            // 15p
            mDevice = "haotian"; //O2
            mModel = "24101PNB7C";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 104) {
            // 14u
            mDevice = "aurora"; // N1
            mModel = "24031PN0DC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 102) {
            // 14p
            mDevice = "shennong"; // N2
            mModel = "23116PN5BC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 155) {
            // civi4p
            mDevice = "chenfeng"; // N9
            mModel = "24053PY09C";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 201) {
            // flip
            mDevice = "ruyi"; // N8
            mModel = "2405CPX3DC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 224) {
            // f4
            mDevice = "goku";  // N18
            mModel = "24072PX77C";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 225) {
            // f4 satellite
            mDevice = "goku";  // N18
            mModel = "24076PX3BC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 223) {
            // f3
            mDevice = "babylon"; // M18
            mModel = "2308CPXD0C";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 190) {
            // alpha
            mDevice = "avenger";
            mModel = "MIX Alpha";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 210) {
            // pad7
            mDevice = "muyu"; //O81
            mModel = "240xxRPxCC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 209) {
            // pad6sp
            mDevice = "sheng";
            mModel = "24018RPACC";
            mManufacturer = "Xiaomi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 307) {
            // k60u
            mDevice = "corot";
            mModel = "23078RKD5C";
            mManufacturer = "Redmi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 310) {
            // k70u
            mDevice = "rothko";
            mModel = "2407FRK8EC";
            mManufacturer = "Redmi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 309) {
            // k70p
            mDevice = "manet";
            mModel = "23117RK66C";
            mManufacturer = "Redmi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 351) {
            // k70e
            mDevice = "duchamp";
            mModel = "23113RKC6C";
            mManufacturer = "Redmi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 335) {
            // t3
            mDevice = "peridot";
            mModel = "24069PC21C";
            mManufacturer = "Redmi";
        } else if (mPrefsMap.getStringAsInt("market_device_modify_new", 0) == 402) {
            // 13c or 13r
            mDevice = "air";
            mModel = "23124RN87C";
            mManufacturer = "Redmi";
        }
        findAndHookConstructor("com.xiaomi.market.MarketApp", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                XposedHelpers.setStaticObjectField(Build.class, "DEVICE", mDevice);
                XposedHelpers.setStaticObjectField(Build.class, "MODEL", mModel);
                XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", mManufacturer);
            }
        });
    }
}
