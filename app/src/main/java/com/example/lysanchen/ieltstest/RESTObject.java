package com.example.lysanchen.ieltstest;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.example.lysanchen.ieltstest.restmodels.Section;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * Created by Krishnapriya  on 23/09/2018.
 */

public class RESTObject {

    static String BASE_URL = "http://192.168.0.7:3000/";
    static String url;
    static String operation;
    static String data;
    static Object[] obj;
     static Object objd;

    public RESTObject() {
    }

    public RESTObject(String url, String operation, String data, Object[] obj) {
        this.url = url;
        this.operation = operation;
        this.data = data;
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }
    public String getFullUrl() {
        return BASE_URL+url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object[] getObj() {
        return obj;
    }

    public void setObj(Object[] obj) {
        this.obj = obj;
    }

    public  Object getObjd() {
        return objd;
    }

    public  void setObjd(Object objd) {
        this.objd = objd;
    }
    /* public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }*/

}
