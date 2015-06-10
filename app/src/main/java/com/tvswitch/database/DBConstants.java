package com.tvswitch.database;

/**
 * Created by Tuong on 5/28/15.
 */
public interface DBConstants {
    public static final String TABLENAME_USERS = "Users";
    public static final String USERS_USERUID = "UserUID";
    public static final String USERS_ACTIVEYN = "ActiveYN";
    public static final String USERS_ENTERPRISEADMINYN = "AdminYN";
    public static final String USERS_LOGINNAME = "LoginName";
    public static final String USERS_PWDHASH = "PwdHash";
    public static final String USERS_FIRSTNAME = "FirstName";
    public static final String USERS_LASTNAME = "LastName";
    public static final String USERS_PHONE = "Phone";
    public static final String USERS_EMAIL = "Email";
    public static final String USERS_NOTES = "Notes";
    public static final String USERS_DATECREATED = "DateCreated";

    public static final String TABLENAME_APPOINTMENT = "Appointment";
    public static final String APPOINTMENT_APPUID = "AppUID";
    public static final String APPOINTMENT_STATUS = "Status";
    public static final String APPOINTMENT_CLIENTNAME= "ClientName";
    public static final String APPOINTMENT_USERUID = "UserUID";
    public static final String APPOINTMENT_SERVICECODE = "ServiceCode";
    public static final String APPOINTMENT_EMPLOYEEID = "EmployeeID";
    public static final String APPOINTMENT_DATETIME = "DateTime";
    public static final String APPOINTMENT_UPLOADYN = "UploadYN";
    public static final String APPOINTMENT_DATECREATED = "DateCreated";
}
