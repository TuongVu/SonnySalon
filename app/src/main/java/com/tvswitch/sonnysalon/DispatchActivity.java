package com.tvswitch.sonnysalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tvswitch.database.Factory_Local;
import com.tvswitch.sonnylibrary.restapi.UserRequests;
import com.tvswitch.sonnylibrary.utils.PrefUtils;
import com.tvswitch.sonnylibrary.utils.User;


public class DispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppStateObjects.setContext(getApplicationContext());
        //check if there is a user exist
        if(AppStateObjects.getUser() == null) {
            doesUserExistedServer();
        }else{
            startActivity(new Intent(DispatchActivity.this, MainActivity.class));
            finish();
        }
    }
    private boolean doesUserExisted(){
        boolean returnValue = false;
        PrefUtils prefs = new PrefUtils(this);
        if(prefs.getUserName() != null && prefs.getPassword() != null){
            if(Factory_Local.validateLoginUser(prefs.getUserName(),prefs.getPassword()) != null) {
                returnValue = true;
            }
        }
        return  returnValue;
    }
    private void doesUserExistedServer(){
        final PrefUtils prefs = new PrefUtils(this);
        if(prefs.getUserName() != null && prefs.getPassword() != null){
            new UserRequests(this).fetchUserDataAsyncTask(prefs.getUserName(), prefs.getPassword(), new UserRequests.UserCallBack() {

                @Override
                public void done(User returnedUser) {
                    if (returnedUser != null) {
                        prefs.setUserName(returnedUser.loginname);
                        prefs.setPassword(returnedUser.pwdhash);
                        AppStateObjects.setUser(returnedUser);
                        startActivity(new Intent(DispatchActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(DispatchActivity.this, LoginActivity.class));
                    }
                    finish();
                }
            });
        }else{
            startActivity(new Intent(DispatchActivity.this, LoginActivity.class));
            finish();
        }
    }

}
