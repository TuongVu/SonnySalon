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
import com.tvswitch.sonnylibrary.restapi.UserRequests;
import com.tvswitch.sonnylibrary.utils.AnimationUtils;
import com.tvswitch.sonnylibrary.utils.HashMD5;
import com.tvswitch.sonnylibrary.utils.PrefUtils;
import com.tvswitch.sonnylibrary.utils.UnicodeInputFilter;
import com.tvswitch.sonnylibrary.utils.User;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LoginActivity extends Activity implements View.OnClickListener{
    private PrefUtils prefs;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnimationUtils.setupWindowAnimations(getWindow());
        prefs = new PrefUtils(this);
        usernameEditText = (EditText) findViewById(R.id.usernameeditText);
        passwordEditText = (EditText) findViewById(R.id.passwordeditText);
        usernameEditText.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        usernameEditText.setFilters(new InputFilter[]{new UnicodeInputFilter()});
        passwordEditText.setFilters(new InputFilter[]{new UnicodeInputFilter()});
        Button login = (Button) findViewById(R.id.login_button);
        Button createNew = (Button) findViewById(R.id.create_new);
        login.setOnClickListener(this);
        createNew.setOnClickListener(this);
        AppStateObjects.setContext(getApplicationContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                loginAction();
                break;
            case R.id.create_new:
                signUpAction();
                break;
        }
    }

    private void loginAction(){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

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
            animateFailedLoginAttempt();
            Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return;
        }

//        if(Factory_Local.validateLoginUser(username,password) != null){
//            prefs.setUserName(username);
//            prefs.setPassword(password);
//            // Start an intent for the dispatch activity
//            Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
////            startActivity(intent,AnimationUtils.getWindowsAnimationBundle(LoginActivity.this));
//            startActivity(intent);
//            finish();
//        }else{
//            animateFailedLoginAttempt();
//            Toast.makeText(LoginActivity.this, getString(R.string.invalid_login), Toast.LENGTH_LONG).show();
//        }

        loginAction(username, HashMD5.md5(password));
    }

    private void loginAction(String loginName, String pwdHash){
        new UserRequests(this).fetchUserDataAsyncTask(loginName, pwdHash, new UserRequests.UserCallBack() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser != null){
                    prefs.setUserName(returnedUser.loginname);
                    prefs.setPassword(returnedUser.pwdhash);
                    AppStateObjects.setUser(returnedUser);
            // Start an intent for the dispatch activity
                    Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
//            startActivity(intent,AnimationUtils.getWindowsAnimationBundle(LoginActivity.this));
                    startActivity(intent);
                    finish();
                }else{
                    AppStateObjects.setUser(null);
                    animateFailedLoginAttempt();
                    Toast.makeText(LoginActivity.this, getString(R.string.invalid_login), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void animateFailedLoginAttempt(){
        YoYo.with(Techniques.Shake).duration(1000).playOn(usernameEditText);
        YoYo.with(Techniques.Shake).duration(1000).playOn(passwordEditText);
    }

    private void signUpAction(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("finisher", new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                LoginActivity.this.finish();
            }
        });
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
//        startActivity(intent, AnimationUtils.getWindowsAnimationBundle(LoginActivity.this));
        AnimationUtils.startWindowsAnimationBundle(LoginActivity.this,intent);
    }

}
