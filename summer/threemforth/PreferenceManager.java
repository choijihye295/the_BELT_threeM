package com.summer.threemforth;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final float DEFAULT_VALUE_FLOAT = -1.0f;
    private static final int DEFAULT_VALUE_INT = 0;
    private static final long DEFAULT_VALUE_LONG = -1;
    private static final String DEFAULT_VALUE_STRING = "";
    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    public static void setString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void setBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void setInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void setLong(Context context, String str, long j) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static void setFloat(Context context, String str, float f) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static String getString(Context context, String str) {
        return getPreferences(context).getString(str, "");
    }

    public static boolean getBoolean(Context context, String str) {
        return getPreferences(context).getBoolean(str, false);
    }

    public static int getInt(Context context, String str) {
        return getPreferences(context).getInt(str, 0);
    }

    public static long getLong(Context context, String str) {
        return getPreferences(context).getLong(str, -1);
    }

    public static float getFloat(Context context, String str) {
        return getPreferences(context).getFloat(str, DEFAULT_VALUE_FLOAT);
    }

    public static void removeKey(Context context, String str) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.remove(str);
        edit.apply();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.clear();
        edit.apply();
    }
}
