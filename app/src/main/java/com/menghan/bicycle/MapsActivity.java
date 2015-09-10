package com.menghan.bicycle;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double lat[];
    private double lng[];
    private String sna[];
    private ArrayList<InfoList> sbi;
    private ArrayList<InfoList> bemp;
    ImageButton listBtn;
    ImageButton markerBtn;
    ImageButton refreshBtn;
    ImageButton weatherBtn;
    String url = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";
    UBikeList uBikeList = new UBikeList(); //可能有問題
    private ArrayList<InfoList> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        initViews();
        //執行異步任務 取得連線
        new HttpAsyncTask().execute(url);

        //取得 json 字串
        String json = getRowData(this, R.raw.ubike);
//        Log.e("value", json);
        //json to pojo
        Gson gson = new GsonBuilder().create();
        //將 json 資料注入至 RetCode 物件
        RetCode code = gson.fromJson(json, RetCode.class);
        int i = 0;
        lat = new double[code.getRetVal().length];
        lng = new double[code.getRetVal().length];
        sna = new String[code.getRetVal().length];
        Log.e("test", String.valueOf(code.getRetVal().length));
        try {
            for (RetVal val : code.getRetVal()) {       //取得retVal的陣列裡的物件
                lat[i] = val.getLat();
                Log.e("test", String.valueOf(val.getLat()));
                lng[i] = val.getLng();
                Log.e("test", String.valueOf(val.getLng()));
                sna[i] = val.getSna();
                Log.e("test", val.getSna());
                i++;
                Log.e("test", String.valueOf(i));
//            RetVal sum = new RetVal(val.getLat(), val.getLng());   //取出要呈現的值
//            Log.e("value", sum.toString());
            }
            Log.e("test", "進入drawmarker");
            drawMarker();
            Log.e("test", "drawMarker...");
        } catch (Exception e) {
            Log.e("test", "發生錯誤...");
        }
    }

    private void initViews() {
        listBtn = (ImageButton) findViewById(R.id.listBtn);
        markerBtn = (ImageButton) findViewById(R.id.markerBtn);
        refreshBtn = (ImageButton) findViewById(R.id.refreshBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.refreshBtn:
                break;
            case R.id.markerBtn:
                break;
            case R.id.weatherBtn:
                break;
            case R.id.listBtn:
                Log.e("btn", "ListBtn");
                intent.setClass(this, UBikeList.class);
                startActivity(intent);
                break;
        }
    }

    private void drawMarker() {
        Log.e("draw", String.valueOf(lat.length));
        sbi = getListItem();
        Log.e("draw", "取得sbi");
        bemp = getListItem();
        Log.e("draw", "取得bemp");
        for (int i = 0; i < lat.length; i++) {
            Log.e("draw", String.valueOf(i));
            String snippet = String.format("可借:%s 可停:%s ",sbi.get(i),bemp.get(i));
            Log.e("draw", snippet);
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).title(sna[i]).snippet(snippet));
            Log.e("draw", "新增"+i+"筆");
        }
    }

    private String getRowData(Context context, int res_id) {
        InputStream is = null;
        InputStreamReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            is = context.getResources().openRawResource(res_id);
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[1];
            while (reader.read(buffer) != -1) {
                sb.append(new String(buffer));
            }
        } catch (Exception e) {

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {

            }
            return sb.toString();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

    }
    public class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            try {
                listItem = new ArrayList<>();
                JSONObject jsonObj = new JSONObject(getJSONData(urls[0]));
                int i = 0;
                Log.e("list", String.valueOf(new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).length()));
                Log.e("list", "正在印出資料...");
                String obj = new JSONObject(jsonObj.getString("result")).getString("results");
                JSONObject arrayString = new JSONArray(obj).getJSONObject(i);
                Log.e("list", obj);
                while (i < new JSONArray(obj).length()) {
                    String sna = arrayString.getString("sna");
                    String ar = arrayString.getString("ar");
                    String sbi = arrayString.getString("sbi");
                    String bemp = arrayString.getString("bemp");
                    InfoList list = new InfoList(sna, ar, sbi, bemp);
                    Log.e("list", "list物件");
                    listItem.add(list);
                    i++;
                    Log.e("list", String.valueOf(i));
                }
            } catch (Exception e) {
            }
            return null;
        }
    }

    public ArrayList<InfoList> getListItem() {
        return listItem;
    }
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
}
