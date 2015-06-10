package com.tvswitch.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
/**
 * Created by Tuong on 5/28/15.
 */
public class PrefUtils {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DEFAULTMAINMENUITEMSELECTED = "DefaultMainMenuItemSelected";
    public static final String MAINMENUITEMSELECTED = "MainMenuItemSelected";
    private SharedPreferences prefs;
    private int currentVersionCode = 0;

    public PrefUtils(Context context) {
        prefs = context.getSharedPreferences(PrefUtils.PREFS_NAME, Context.MODE_PRIVATE);
        try {
            currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gets value from SharedPreferences under Username key
     *
     * @return value or null if no value found
     */
    public String getUserName() {
        return prefs.getString(USERNAME, null);
    }
    /**
     * Stores value in SharedPreferences under username key
     *
     * @param value
     * @return true if value successfully written to SharedPreferences
     */
    public boolean setUserName(String value) {
        return prefs.edit().putString(USERNAME, value).commit();
    }

    /**
     * Gets value from SharedPreferences under Password key
     *
     * @return value or null if no value found
     */
    public String getPassword() {
        return prefs.getString(PASSWORD, null);
    }

    /**
     * Stores value in SharedPreferences under password key
     *
     * @param value
     * @return true if value successfully written to SharedPreferences
     */
    public boolean setPassword(String value) {
        return prefs.edit().putString(PASSWORD, value).commit();
    }

    public int getMainMenuItemSelected(){
        return prefs.getInt(MAINMENUITEMSELECTED, 0);
    }

    public boolean setMainMenuItemSelected(int position){
        return prefs.edit().putInt(MAINMENUITEMSELECTED, position).commit();
    }

    public int getDefaultMainMenuItemSelected(){
        return prefs.getInt(DEFAULTMAINMENUITEMSELECTED,-1);
    }

    public boolean setDefaultMainMenuItemSelected(int position){
        return prefs.edit().putInt(DEFAULTMAINMENUITEMSELECTED, position).commit();
    }
}
