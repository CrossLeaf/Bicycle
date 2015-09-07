package com.menghan.bicycle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class UBikeList extends Activity {
    ArrayAdapter<String> listAdapter;
    String url = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_list);

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return getJSONData(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObj = new JSONObject(result);
                String sna = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(0).getString("sna");
                String ar = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(0).getString("ar");
                String sbi = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(0).getString("sbi");
                String bemp = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(0).getString("bemp");
//                etResponse.setText(sna);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 使用 HttpGet 進行連線，取得 JSON 資料
    private String getJSONData(String url) {
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
            } else {
                retSrc = "Did not work!";
            }

        } catch (Exception e) {
            Log.e("retSrc", "讀取JSON Error...");
//            Log.e("retSrc", e.getMessage());
            return null;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return retSrc;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
