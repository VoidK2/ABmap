

package com.majie.abmap.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;

/**
 * LOG工具
 *
 *  @author majie
 */
public class LogUtils {
    public static final String TAG = "breeze";
    private static boolean IS_DEBUG = false;

    /**
     * 是否为debug状态
     *
     * @return
     */
    public static boolean isDebug() {
        return IS_DEBUG;
    }

    /**
     * 设置是否debug
     *
     * @param debug true/false
     */
    public static void setDebug(boolean debug) {
        IS_DEBUG = debug;
    }

    /**
     * 在控制台中输出debug信息
     *
     * @param msg 信息
     */
    public static void debug(String msg) {
        if (IS_DEBUG)
            Log.d(TAG, msg);
    }

    /**
     * 在控制台中输出error信息
     *
     * @param msg 信息
     */
    public static void error(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * 在控制台中输出error信息
     *
     * @param msg 信息
     * @param tr
     */
    public static void error(String msg, Throwable tr) {
        if (IS_DEBUG)
            Log.e(TAG, msg, tr);
    }

    /**
     * 在控制台中输出info信息
     *
     * @param msg 信息
     */
    public static void info(String msg) {
        if (IS_DEBUG)
            Log.i(TAG, msg);
    }

    /**
     * 在控制台中输出debug信息
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void debug(String tag, String msg) {
        if (IS_DEBUG)
            Log.d(tag, msg);
    }

    /**
     * 在控制台中输出error信息
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void error(String tag, String msg) {
        if (IS_DEBUG)
            Log.e(tag, msg);
    }

    /**
     * 在控制台中输出error信息
     *
     * @param tag 标签
     * @param msg 信息
     * @param tr
     */
    public static void error(String tag, String msg, Throwable tr) {
        if (IS_DEBUG)
            Log.e(tag, msg, tr);
    }

    /**
     * 在控制台中输出info信息
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void info(String tag, String msg) {
        if (IS_DEBUG)
            Log.i(tag, msg);
    }

    /**
     * 写入log文件
     *
     * @param msg 信息
     */
    public static void saveLog(final Context context, String msg) {
        final String string = "[" + TimeUtils.getSystemTime("HH:mm:ss") + "]" + msg + "\n";

        debug(msg);

        //判断是否有sd卡读写权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //开启新线程写入日志
        new Thread() {
            @Override
            public void run() {
                File dir = context.getExternalFilesDir("log");
                if (null == dir) {
                    return;
                }
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File logFile = new File(dir, "log" + TimeUtils.getSystemTime("yyyyMMdd") + ".txt");
                FileUtils.writeFileToSDCard(logFile, string);
            }
        }.start();


    }

}
