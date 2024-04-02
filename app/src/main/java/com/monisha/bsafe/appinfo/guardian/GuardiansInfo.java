package com.monisha.bsafe.appinfo.guardian;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class GuardiansInfo {
    private static final String KEY_GUARDIANS_PRIMARY = "key_guardian_primary";
    private static final String KEY_GUARDIANS_SECONDARY = "key_guardian_secondary";
    private static final String PREF_NAME = "guardians";

    public static String getPrimaryGuardian(Application application) {
        SharedPreferences sharedPref = application
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_GUARDIANS_PRIMARY, "");
    }

    public static void putPrimaryGuardian(Activity activity, String guardianNumber) {
        SharedPreferences sharedPref = activity.getApplication()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_GUARDIANS_PRIMARY, guardianNumber);
        editor.apply();
    }

    public static String getSecondaryGuardian(Application application) {
        SharedPreferences sharedPref = application
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_GUARDIANS_SECONDARY, "");
    }

    public static void putSecondaryGuardian(Activity activity, String guardianNumber) {
        SharedPreferences sharedPref = activity.getApplication()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_GUARDIANS_SECONDARY, guardianNumber);
        editor.apply();
    }
}
