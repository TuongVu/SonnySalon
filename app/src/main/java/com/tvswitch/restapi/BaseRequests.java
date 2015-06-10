package com.tvswitch.restapi;

import android.app.ProgressDialog;
import android.content.Context;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by Tuong on 6/7/15.
 */
public class BaseRequests {
    protected ProgressDialog progressDialog;
    protected static final int CONNECTION_TIMEOUT = 1000 * 15;
    protected static final String SERVER_ADDRESS = "http://tvswitch.net63.net/";

    protected void initProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    protected HttpParams getHttpRequestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpRequestParams,
                CONNECTION_TIMEOUT);
        return httpRequestParams;
    }
}
