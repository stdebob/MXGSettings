// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.mxg.settings.R;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivityContextHelper {

    private Context context;

    public MainActivityContextHelper(Context context) {
        this.context = context;
    }

    @SuppressLint("HardwareIds")
    public String getAndroidId() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public boolean verifyDexCRC() {
        String dexCrcStr = context.getResources().getString(R.string.crc_value);
        if(dexCrcStr.startsWith("Error")) return false;
        //long dexCrc = Long.parseLong("613BD799");

        //String orginalCrc = getString(R.string.str_code);
        ZipFile zf;
        try {
            zf = new ZipFile(context.getApplicationContext().getPackageCodePath());
            ZipEntry ze = zf.getEntry("classes.dex");
            long decCrc = ze.getCrc();
            String strCrc = Long.toHexString(decCrc);
            strCrc = strCrc.toUpperCase();
            //String MD5Crc = MD5Util.GetMD5Code(strCrc);
            //Log.e("checkcrc", MD5Crc);
            if (dexCrcStr.equals(strCrc)) {
                //ActivityManagerUtil.getScreenManager().removeAllActivity();
                return true;
            } else return false;
        } catch (IOException e) {
            return false;
        }
    }

    public String getDexCRC() {
        String dexCrcStr = context.getResources().getString(R.string.crc_value);
        if(dexCrcStr.startsWith("Error")) return dexCrcStr;
        //long dexCrc = Long.parseLong("613BD799");

        //String orginalCrc = getString(R.string.str_code);
        ZipFile zf;
        try {
            zf = new ZipFile(context.getApplicationContext().getPackageCodePath());
            ZipEntry ze = zf.getEntry("classes.dex");
            long decCrc = ze.getCrc();
            String strCrc = Long.toHexString(decCrc);
            strCrc = strCrc.toUpperCase();
            //String MD5Crc = MD5Util.GetMD5Code(strCrc);
            //Log.e("checkcrc", MD5Crc);
            if (dexCrcStr.equals(strCrc)) {
                //ActivityManagerUtil.getScreenManager().removeAllActivity();
                return dexCrcStr + " = " + strCrc;
            } else return dexCrcStr + " != " + strCrc;
        } catch (IOException e) {
            return e.toString();
        }
    }
}
