package com.menghan.bicycle;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MengHan on 2015/9/3.
 */
public class UbikeAPI {
    String api = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";
//    String api = "http://opendata.epa.gov.tw/ws/Data/WRRecyc/?$orderby=FacilityAddress&$skip=0&$top=10&format=json";
    public String getAPI(){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(api);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(10000);
            Log.e("url", "Connection success");
            urlConnection.setRequestMethod("GET");
            Log.e("url", "request success");
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String jsonString1 = reader.readLine();
            Log.e("url", jsonString1);
            reader.close();
            String jsonString = jsonString1;
            JSONObject jsonObj = new JSONObject(jsonString);
            return " "+jsonObj.getJSONObject("result").opt("result");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        finally {
            if (urlConnection != null)
            urlConnection.disconnect();
        }
    }
}
