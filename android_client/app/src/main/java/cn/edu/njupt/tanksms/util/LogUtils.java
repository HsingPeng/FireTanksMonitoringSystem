package cn.edu.njupt.tanksms.util;

import android.util.Log;

import cn.edu.njupt.tanksms.Config;

/**
 * Created by DEEP on 2015/1/23.
 */
public class LogUtils {

    public static void LOGV(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (Config.IS_DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void LOGD(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (Config.IS_DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void LOGI(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (Config.IS_DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void LOGW(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (Config.IS_DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void LOGE(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (Config.IS_DEBUG) {
            Log.e(tag, message);
        }
    }

}
