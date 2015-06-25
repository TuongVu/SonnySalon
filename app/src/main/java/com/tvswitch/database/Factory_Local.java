package com.tvswitch.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tvswitch.sonnylibrary.utils.HashMD5;
import com.tvswitch.sonnysalon.AppStateObjects;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tuong on 5/28/15.
 */
public class Factory_Local {
    public static Local_DB local_db = new Local_DB(AppStateObjects.getContext());
    public static SQLiteDatabase db = local_db.getWritableDatabase(); //only one database connection use per factory

    /**
     * Validate login user for login activity
     * @param username
     * @param password
     * @return
     */
    public static String validateLoginUser(String username, String password) {
        String hashPwd = HashMD5.md5(password);
        String userUID = null;
        Cursor cursor = null;
        try {
        cursor = db.rawQuery("select * from " + DBConstants.TABLENAME_USERS + " where " + DBConstants.USERS_LOGINNAME + " like '"
                + username + "' and " + DBConstants.USERS_PWDHASH + " = '" + hashPwd + "';", null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            userUID = cursor.getString(cursor.getColumnIndex(DBConstants.USERS_USERUID));
            AppStateObjects.setUserUID(userUID);
            AppStateObjects.setUserFullName(cursor.getString(cursor.getColumnIndex(DBConstants.USERS_FIRSTNAME)) + " "
                    + cursor.getString(cursor.getColumnIndex(DBConstants.USERS_LASTNAME)));
        }
        }catch(Exception e){ //catch all exceptions
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close(); //close cursor to prevent memory leak
            }
        }
        return userUID;
    }

    /**
     * This method check if the username is already taken
     * @param username
     * @return boolean value
     */
    public static boolean doesUserNameExist(String username) {
        boolean exist = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select count(*) from " + DBConstants.TABLENAME_USERS + " where " + DBConstants.USERS_LOGINNAME + " = '"
                    + username + "';", null);
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                int s = cursor.getInt(0);
                if (s == 1) {
                    exist = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return exist;
    }

    /**
     * This method will create a new user with valid username and password
     * @param map
     * @return
     */
    public static long createNewUser(HashMap<String,String>map){
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.USERS_USERUID, UUID.randomUUID().toString());
            values.put(DBConstants.USERS_FIRSTNAME, map.get(DBConstants.USERS_FIRSTNAME));
            values.put(DBConstants.USERS_LASTNAME, map.get(DBConstants.USERS_LASTNAME));
            values.put(DBConstants.USERS_LOGINNAME, map.get(DBConstants.USERS_LOGINNAME));
            values.put(DBConstants.USERS_PWDHASH, HashMD5.md5(map.get(DBConstants.USERS_PWDHASH)));
            values.put(DBConstants.USERS_ENTERPRISEADMINYN, 0);
            values.put(DBConstants.USERS_ACTIVEYN, 1);
            values.put(DBConstants.USERS_PHONE, "");
            values.put(DBConstants.USERS_EMAIL, "");
            values.put(DBConstants.USERS_NOTES, "");
            values.put(DBConstants.USERS_DATECREATED, System.currentTimeMillis());
            id = db.insertOrThrow(DBConstants.TABLENAME_USERS, null, values);
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
