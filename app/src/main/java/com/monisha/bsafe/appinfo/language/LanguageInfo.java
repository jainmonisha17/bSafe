package com.monisha.bsafe.appinfo.language;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LanguageInfo {
    private static final String KEY_LANGUAGE = "key_language";
    private static final String PREF_NAME = "language";

    public static String getLanguage(Activity activity) {
        SharedPreferences sharedPref = activity.getApplication()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_LANGUAGE, Languages.ENGLISH);
    }

    public static void putLanguage(Activity activity, String newLanguage) {
        SharedPreferences sharedPref = activity.getApplication()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_LANGUAGE, newLanguage);
        editor.apply();
    }
}
