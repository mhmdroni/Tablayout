package com.example.tablayout.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String PREFS_NAME = "pref_dictionary";
    private static final String APP_FIRST_RUN = "app_first_run";
    private final SharedPreferences prefs;

    public AppPreference(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /***
     * Gunakan method ini untuk menandai aplikasi pertama kali di jalankan
     * @param input
     */
    public void setAppFirstRun(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(APP_FIRST_RUN, input);
        editor.apply();
    }

    public Boolean getFirstRun(){
        return prefs.getBoolean(APP_FIRST_RUN, true);
    }
}
