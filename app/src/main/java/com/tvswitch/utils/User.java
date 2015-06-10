package com.tvswitch.utils;

/**
 * Created by Tuong on 6/4/15.
 */
public class User {
    public String firstname, lastname, loginname, pwdhash, useruid;

    public User(String firstname, String lastname, String loginname, String pwdhash, String useruid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.loginname = loginname;
        this.pwdhash = pwdhash;
        this.useruid = useruid;
    }
}
