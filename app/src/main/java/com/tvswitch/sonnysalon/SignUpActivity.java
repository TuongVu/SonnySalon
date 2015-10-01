package com.tvswitch.sonnysalon;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tvswitch.utils.AnimationUtils;
import com.tvswitch.utils.PrefUtils;
import com.tvswitch.utils.UnicodeInputFilter;

import java.util.UUID;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SignUpActivity extends Activity implements View.OnClickListener{
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private PrefUtils prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnimationUtils.setupWindowAnimations(getWindow());
        prefs = new PrefUtils(this);
        usernameEditText = (EditText) findViewById(R.id.usernameeditText);
        passwordEditText = (EditText) findViewById(R.id.passwordeditText);
        firstnameEditText = (EditText) findViewById(R.id.firstnameeditText);
        lastnameEditText = (EditText) findViewById(R.id.lastnameeditText);
        usernameEditText.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        InputFilter[] filter = new InputFilter[]{new UnicodeInputFilter()};
        usernameEditText.setFilters(filter);
        passwordEditText.setFilters(filter);
        firstnameEditText.setFilters(filter);
        lastnameEditText.setFilters(filter);
        ((Button) findViewById(R.id.savebutton)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String firstname = firstnameEditText.getText().toString().trim();
        String lastname = lastnameEditText.getText().toString().trim();

        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return;
        }

//        if(Factory_Local.doesUserNameExist(username)){
//            YoYo.with(Techniques.Shake).duration(1000).playOn(usernameEditText);
//            Toast.makeText(SignUpActivity.this, getString(R.string.already_existed), Toast.LENGTH_LONG).show();
//        }else{
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put(DBConstants.USERS_FIRSTNAME,firstname);
//            map.put(DBConstants.USERS_LASTNAME,lastname);
//            map.put(DBConstants.USERS_LOGINNAME,username);
//            map.put(DBConstants.USERS_PWDHASH, password);
//            if(Factory_Local.createNewUser(map) > 0){
//                prefs.setUserName(username);
//                prefs.setPassword(password);
//                // Start an intent for the dispatch activity
//                Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                ((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
//                finish();
//            }
//        }

//        User user = new User(firstname,lastname,username, HashMD5.md5(password), UUID.randomUUID().toString());
//        storeOnServer(user);
    }

//    private void storeOnServer(User user){
//            new UserRequests(this).storeUserDataInBackground(user, new UserRequests.UserCallBack() {
//                @Override
//                public void done(User returnedUser) {
//                    if(returnedUser != null){
//                        AppStateObjects.setUser(returnedUser);
//                        prefs.setUserName(returnedUser.loginname);
//                        prefs.setPassword(returnedUser.pwdhash);
//                        Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        ((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
//                        finish();
//                    }else{
//                        YoYo.with(Techniques.Shake).duration(1000).playOn(usernameEditText);
//                        Toast.makeText(SignUpActivity.this, getString(R.string.already_existed), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//    }
}
