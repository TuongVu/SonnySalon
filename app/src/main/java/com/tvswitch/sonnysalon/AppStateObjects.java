package com.tvswitch.sonnysalon;

import android.content.Context;

import com.tvswitch.utils.User;

/**
 * Created by Tuong on 5/28/15.
 */
public class AppStateObjects {
    private static volatile Context context;
    private static String userFullName;
    private static String userUID;
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppStateObjects.user = user;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppStateObjects.context = context;
    }

    public static String getUserFullName() {
        return userFullName;
    }

    public static void setUserFullName(String userFullName) {
        AppStateObjects.userFullName = userFullName;
    }

    public static String getUserUID() {
        return userUID;
    }

    public static void setUserUID(String userUID) {
        AppStateObjects.userUID = userUID;
    }
}
