package com.menghan.bicycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;


public class UBikeList extends Activity {
    ArrayAdapter<String> listAdapter;
    String url = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_list);
        getJSONData();
    }

    // 使用 HttpGet 進行連線，取得 JSON 資料
    private JSONArray getJSONData() {
        String retSrc = "";
        HttpGet httpget = new HttpGet(url);
        HttpClient httpclient = new DefaultHttpClient();
        Log.e("retSrc", "讀取 JSON-1...");
        try {
            HttpResponse response = httpclient.execute(httpget);
            Log.e("retSrc", "讀取 JSON-2...");
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                retSrc = EntityUtils.toString(resEntity);
                Log.e("retSrc", "讀取 JSON-3...");
            }
            //Log.e("retSrc", retSrc); //將讀取的JSON顯示
            JSONArray content = new JSONArray(retSrc);
            return content;
        } catch (Exception e) {
            Log.e("retSrc", "讀取JSON Error...");
//            Log.e("retSrc", e.getMessage());
            return null;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
}
