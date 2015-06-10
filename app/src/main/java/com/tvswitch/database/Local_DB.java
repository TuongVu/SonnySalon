package com.tvswitch.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Tuong on 5/28/15.
 */
public class Local_DB extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "sonnylocal.db";
    private static final int DATABASE_VERSION = 2;  //1 - Create user table
                                                    //2 - Create appointment table

    public Local_DB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private String createUsersTable() {
        return "CREATE TABLE " + DBConstants.TABLENAME_USERS + " ("
                + DBConstants.USERS_USERUID + " TEXT PRIMARY KEY, "
                + DBConstants.USERS_ACTIVEYN + " INTEGER, "
                + DBConstants.USERS_ENTERPRISEADMINYN + " INTEGER, "
                + DBConstants.USERS_LOGINNAME + " TEXT, "
                + DBConstants.USERS_PWDHASH + " TEXT, "
                + DBConstants.USERS_FIRSTNAME + " TEXT, "
                + DBConstants.USERS_LASTNAME + " TEXT, "
                + DBConstants.USERS_PHONE + " TEXT, "
                + DBConstants.USERS_EMAIL + " TEXT, "
                + DBConstants.USERS_NOTES + " TEXT, "
                + DBConstants.USERS_DATECREATED + " INTEGER);";
    }

    private String createAppointmentTable() {
        return "CREATE TABLE " + DBConstants.TABLENAME_APPOINTMENT + " ("
                + DBConstants.APPOINTMENT_APPUID + " TEXT PRIMARY KEY, "
                + DBConstants.APPOINTMENT_EMPLOYEEID + " INTEGER, "
                + DBConstants.APPOINTMENT_CLIENTNAME + " TEXT, "
                + DBConstants.APPOINTMENT_USERUID + " TEXT, "
                + DBConstants.APPOINTMENT_SERVICECODE + " TEXT, "
                + DBConstants.APPOINTMENT_STATUS + " TEXT, "
                + DBConstants.APPOINTMENT_UPLOADYN + " TEXT, "
                + DBConstants.APPOINTMENT_DATETIME + " TEXT, "
                + DBConstants.USERS_DATECREATED + " INTEGER);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUsersTable());
        db.execSQL(createAppointmentTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > 1){ //update existing app to version 2
            db.execSQL(createAppointmentTable());
        }
    }
}
