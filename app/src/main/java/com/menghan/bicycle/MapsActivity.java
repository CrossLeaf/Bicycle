package com.menghan.bicycle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.playlog.internal.LogEvent;
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
    private LatLng myLatLng;
    private Location myLocation;
    CameraPosition cameraPosition;
    CameraUpdate cameraUpdate;
    private LocationManager lc;
    private double lat[];
    private double lng[];
    private String sna[];
    private ArrayList<InfoList> sbi;
    private ArrayList<InfoList> bemp;
    ImageButton fabBtn;
    String url = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=ddb80380-f1b3-4f8e-8016-7ed9cba571d5";
    private static ArrayList<InfoList> list;
    private ArrayList<MarkerList> markerLists;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setUpMapIfNeeded();
        //執行異步任務 取得連線
        new HttpAsyncTask().execute(url);

        fabBtn = (ImageButton) findViewById(R.id.fab);
        fabBtn.setOnClickListener(new FabListener());

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

    class FabListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("gps", "Fab");
            LocationListener locationChange = new MyLocationListener();

            Log.e("gps", "init");
            if (!lc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("定位管理")
                        .setMessage("GPS尚未開啟.\n是否要開啟 GPS ?")
                        .setPositiveButton("啟用", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("不啟用", null).show();
            }
            Log.e("gps", "diaLog");
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setMyLocationEnabled(true);
            Location location = lc.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.e("gps", "gps:"+location);
                myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                cameraPosition = new CameraPosition.Builder().target(myLatLng).zoom(15).build();
                cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate);
            lc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100.0f, locationChange);
            Log.e("gps", "已定位");
        }
    }

    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location mLocation) {
            Toast.makeText(MapsActivity.this, "目前經度:"+mLocation.getLatitude()+"目前緯度:"+mLocation.getLongitude(),Toast.LENGTH_LONG);
            myLocation = mLocation;
            myLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
//            cameraPosition = new CameraPosition.Builder().target(myLatLng).zoom(15).build();
//            cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
//            mMap.animateCamera(cameraUpdate);
            Toast.makeText(MapsActivity.this, "經緯度座標變更...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    void initDrawMarker(ArrayList<MarkerList> markerLists) {
        int data = 10;
        if (count % 10 != 0) {
            data = count % 10;
        }
        Log.e("draw", "data:" + data);
        for (int i = count - data; i <= count - 1; i++) {
            String snippet = String.format("可借:%s 可停:%s ",
                    markerLists.get(i).getSbi(), markerLists.get(i).getBemp());
            Log.e("draw", snippet);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(markerLists.get(i).getLat(), markerLists.get(i).getLng()))
                    .title(markerLists.get(i).getSna())
                    .snippet(snippet));
            Log.e("draw", "新增" + i + "筆");
            Log.e("draw", markerLists.get(i).getSna());
        }

        Log.e("draw", "Init DrawMarker 完成");
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
        LatLng latLng = new LatLng(25.0408578889, 121.567904444);
//        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));

    }

    public class HttpAsyncTask extends AsyncTask<String, ArrayList<MarkerList>, ArrayList<InfoList>> {
        @Override
        protected ArrayList<InfoList> doInBackground(String... urls) {
            try {
                String sna;
                String ar;
                String sbi;
                String bemp;
                String lat;
                String lng;
                list = new ArrayList<>();
                markerLists = new ArrayList<>();
                JSONObject jsonObj = new JSONObject(getJSONData(urls[0]));
                int i = 0;
                Log.e("list", String.valueOf(new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results")).length()));
                Log.e("list", "正在印出資料...");
                JSONArray array = new JSONArray(new JSONObject(jsonObj.getString("result")).getString("results"));
                Log.e("list", "array長度:" + array.length());
                while (i < array.length()) {
                    Log.e("list", String.valueOf(i));
                    JSONObject arrayString = array.getJSONObject(i);
                    sna = arrayString.getString("sna");
                    ar = arrayString.getString("ar");
                    sbi = arrayString.getString("sbi");
                    bemp = arrayString.getString("bemp");
                    lat = arrayString.getString("lat");
                    lng = arrayString.getString("lng");
                    InfoList listItem = new InfoList(sna, ar, sbi, bemp);
                    list.add(listItem);
                    MarkerList markerItem = new MarkerList(sna, sbi, bemp, lat, lng);
                    markerLists.add(markerItem);
                    Log.e("list", list.get(i).getAr());

                    i++;
                    if (i % 10 == 0 || i == array.length()) {
                        count = i;

                        Log.e("list", "count:" + count);
                        publishProgress(markerLists);
                    }
                }
            } catch (Exception e) {
                Log.e("list", "連線出錯");
            }
            return list;
        }

        @Override
        protected void onProgressUpdate(ArrayList<MarkerList>... values) {
            super.onProgressUpdate(values);
            initDrawMarker(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<InfoList> lists) {
//            drawMarker();
        }
    }


    public ArrayList<InfoList> getList() {

        return list;
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("circleLife", "pause");
//        Log.e("circleLife", list.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("circleLife", "stop");
        new HttpAsyncTask().cancel(true);
//        Log.e("circleLife", list.toString());
    }
}
