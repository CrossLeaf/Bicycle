package com.menghan.bicycle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class UBikeList extends Activity {
//    private ArrayList<InfoList> listItem;
    ListView listView;
//    String sna;
//    String ar;
//    String sbi;
//    String bemp;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_list);
        listView = (ListView) findViewById(R.id.infoList);
        MapsActivity main = new MapsActivity();
        adapter = new InfoAdapter(UBikeList.this, main.getListItem());

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
    }

//    public class HttpAsyncTask extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... urls) {
//            try {
//                listItem = new ArrayList<>();
//                JSONObject jsonObj = new JSONObject(getJSONData(urls[0]));
//                int i = 0;
//                Log.e("list", String.valueOf(new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).length()));
//                Log.e("list", "正在印出資料...");
//                String obj = new JSONObject(jsonObj.getString("result")).getString("results");
//                JSONObject arrayString = new JSONArray(obj).getJSONObject(i);
//                Log.e("list", obj);
//                while (i < new JSONArray(obj).length()) {
//                    sna = arrayString.getString("sna");
//                    ar = arrayString.getString("ar");
//                    sbi = arrayString.getString("sbi");
//                    bemp = arrayString.getString("bemp");
//                    InfoList list = new InfoList(sna, ar, sbi, bemp);
//                    Log.e("list", "list物件");
//                    listItem.add(list);
//                    i++;
//                    Log.e("list", String.valueOf(i));
//                }
//            } catch (Exception e) {
//            }
//            return null;
//        }
//    }
//
//    public ArrayList<InfoList> getListItem() {
//        return listItem;
//    }

    // 使用 HttpGet 進行連線，取得 JSON 資料
//    private String getJSONData(String url) {
//        String retSrc = "";
//        HttpGet httpget = new HttpGet(url);
//        HttpClient httpclient = new DefaultHttpClient();
//        Log.e("retSrc", "讀取 JSON-1...");
//        try {
//            HttpResponse response = httpclient.execute(httpget);
//            Log.e("retSrc", "讀取 JSON-2...");
//            HttpEntity resEntity = response.getEntity();
//            if (resEntity != null) {
//                retSrc = EntityUtils.toString(resEntity);
//                Log.e("retSrc", "讀取 JSON-3...");
//            } else {
//                retSrc = "Did not work!";
//            }
//
//        } catch (Exception e) {
//            Log.e("retSrc", "讀取JSON Error...");
//            return null;
//        } finally {
//            httpclient.getConnectionManager().shutdown();
//        }
//        return retSrc;
//    }


}
