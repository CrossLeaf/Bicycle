package com.menghan.bicycle;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        //取得 json 字串
        String json = getRowData(this, R.raw.ubike);
//        Log.e("value", json);
        //json to pojo
        Gson gson = new GsonBuilder().create();
        //將 json 資料注入至 RetCode 物件
        RetCode code = gson.fromJson(json, RetCode.class);
        int i = 0;
        lat = new double [381];
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
        }catch (Exception e){

        }
    }

    private void drawMarker() {
        Log.e("draw", String.valueOf(lat.length));
        for (int i=0; i<lat.length; i++){
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

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

    }
}
