package com.step.counting.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
    private static final String PREFERENCE_NAME_BASIC_CONFIG = "basic_config";
    private static final String PREFERENCE_SOTR_TYPE = "sort_type";
    private static final String PREFERENCE_SHOW_HIDE_FILES = "show_hide_files";
    private static final String PREFERENCE_FIRST_TIME_USE = "first_time_use";

    public static void setFirstTimeUse(boolean isFirst,Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME_BASIC_CONFIG, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREFERENCE_FIRST_TIME_USE, isFirst);
        editor.commit();
    }

    //是否第一次使用
    public static boolean getFirstTimeUse(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME_BASIC_CONFIG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREFERENCE_FIRST_TIME_USE, true);
    }

}
