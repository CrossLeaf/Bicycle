package com.menghan.bicycle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

import java.util.ArrayList;


public class UBikeList extends Activity {
    String url = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";
    private ArrayList<InfoList> listItem;
    private InfoAdapter adapter;
    ListView listView;
    String sna;
    String ar;
    String sbi;
    String bemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_list);
        listView = (ListView) findViewById(R.id.infoList);
        new HttpAsyncTask().execute(url);
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
                listItem = new ArrayList<>();
                adapter = new InfoAdapter(UBikeList.this, listItem);
                JSONObject jsonObj = new JSONObject(result);
                int i = 0;
                Toast.makeText(UBikeList.this, "正在抓取資料...", Toast.LENGTH_LONG).show();
                Log.e("list", String.valueOf(new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).length()));
                Log.e("list", "正在印出資料...");
                while (i < new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).length()) {
                    sna = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(i).getString("sna");
                    ar = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(i).getString("ar");
                    sbi = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(i).getString("sbi");
                    bemp = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).getJSONObject(i).getString("bemp");
                    InfoList list = new InfoList(sna, ar, sbi, bemp);
                    listItem.add(list);
                    i++;

                }
                Log.e("list", "adapter...");
//                adapter = new InfoAdapter(UBikeList.this, listItem);
                listView.setAdapter(adapter);
                listView.setTextFilterEnabled(true);

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
            return null;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return retSrc;
    }

//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while ((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }
}
