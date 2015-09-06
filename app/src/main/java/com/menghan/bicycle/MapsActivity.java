package com.menghan.bicycle;

import android.content.Context;
import android.content.Intent;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double lat[];
    private double lng[];
    ImageButton listBtn;
    ImageButton markerBtn;
    ImageButton refreshBtn;
    ImageButton weatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        initViews();
        //取得 json 字串
        String json = getRowData(this, R.raw.ubike);
//        Log.e("value", json);
        //json to pojo
        Gson gson = new GsonBuilder().create();
        //將 json 資料注入至 RetCode 物件
        RetCode code = gson.fromJson(json, RetCode.class);
        int i = 0;
        lat = new double[381];
        lng = new double[381];
        try {
            for (RetVal val : code.getRetVal()) {       //取得retVal的陣列裡的物件
                Log.e("test", String.valueOf(val.getLat()));
                Log.e("test", String.valueOf(i));
                lat[i] = val.getLat();
                lng[i] = val.getLng();
                i++;
//            RetVal sum = new RetVal(val.getLat(), val.getLng());   //取出要呈現的值
//            Log.e("value", sum.toString());

            }
            drawMarker();
        } catch (Exception e) {

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
//    private void changeFragment(Fragment f) {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.fragment_container, f);
//        transaction.commit();
//    }

    private void drawMarker() {
        Log.e("draw", String.valueOf(lat.length));
        for (int i = 0; i < lat.length; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).title("yes"));
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
}
