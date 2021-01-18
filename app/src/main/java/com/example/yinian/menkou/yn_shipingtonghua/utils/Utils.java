package com.example.yinian.menkou.yn_shipingtonghua.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Utils {

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String binaryToString(byte[] binary) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("0x%02X", binary[0])).append(' ');
        builder.append(String.format("0x%02X", binary[1])).append(' ');
        builder.append(String.format("0x%02X", binary[2])).append(' ');
        for (int i = 0; i < 9; i++) {
            builder.append(byteToBinary(binary[3 + i])).append(' ');
        }
        builder.append(String.format("%d", binary[12])).append(' ');
        builder.append(String.format("%d", binary[13])).append(' ');
        builder.append(String.format("%d", binary[14])).append(' ');
        builder.append(String.format("0x%02X", binary[15])).append(' ');
        builder.append(String.format("0x%02X", binary[16])).append(' ');
        return builder.toString();
    }

    public static int byteToInt(byte value) {
        return ((int) value) & 0xFF;
    }

    public static String byteToBinary(byte value) {
        int iv = byteToInt(value);
        String str = Integer.toString(iv, 2);
        if (str.length() < 8) {
            int count = 8 - str.length();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                builder.append('0');
            }
            builder.append(str);
            return builder.toString();
        }
        return str;
    }

    public static String cmdToString(byte[] cmd) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("0x%02X", cmd[0])).append(' ');
        builder.append(String.format("0x%02X", cmd[1])).append(' ');
        builder.append(String.format("0x%02X", cmd[2])).append(' ');
        builder.append(byteToBinary(cmd[3])).append(' ');
        builder.append(byteToInt(cmd[4])).append(' ');
        builder.append(byteToInt(cmd[5])).append(' ');
        builder.append(byteToInt(cmd[6])).append(' ');
        return builder.toString();
    }

}
