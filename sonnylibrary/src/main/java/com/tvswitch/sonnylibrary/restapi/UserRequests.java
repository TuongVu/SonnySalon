package com.tvswitch.sonnylibrary.restapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.tvswitch.sonnylibrary.utils.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tuong on 6/4/15.
 */
public class UserRequests extends BaseRequests{

    public UserRequests(Context context) {
        super.initProgressDialog(context);
    }

    public interface UserCallBack{
        public abstract void done(User returnedUser);
    }

    public void storeUserDataInBackground(User user, UserCallBack userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataAsyncTask(String loginName, String pwdHash, UserCallBack userCallBack) {
        progressDialog.show();
        new fetchUserDataAsyncTask(loginName,pwdHash, userCallBack).execute();
    }

    /**
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        UserCallBack userCallBack;

        public StoreUserDataAsyncTask(User user, UserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("firstname", user.firstname));
            dataToSend.add(new BasicNameValuePair("lastname", user.lastname));
            dataToSend.add(new BasicNameValuePair("loginname", user.loginname));
            dataToSend.add(new BasicNameValuePair("pwdhash", user.pwdhash));
            dataToSend.add(new BasicNameValuePair("useruid", user.useruid));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "Register.php");
            User returnUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.i("info", result);
                JSONObject jObject = new JSONObject(result);
                if (jObject.length() != 0){
                    returnUser = user;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnUser;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            progressDialog.dismiss();
            userCallBack.done(user);
        }

    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        UserCallBack userCallBack;
        String loginname;
        String pwdhash;

        public fetchUserDataAsyncTask(String loginName,String pwdHash, UserCallBack userCallBack) {
            this.loginname = loginName;
            this.pwdhash = pwdHash;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("loginname", loginname));
            dataToSend.add(new BasicNameValuePair("pwdhash", pwdhash));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "Login.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0){
                    returnedUser = new User(jObject.getString("firstname"),jObject.getString("lastname"),jObject.getString("loginname"),jObject.getString("pwdhash"),jObject.getString("useruid"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
        }
    }
}
