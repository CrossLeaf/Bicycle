package com.menghan.bicycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class UBikeList extends Activity {
    //    private ArrayList<InfoList> listItem;
    ListView listView;
    //    String sna;
//    String ar;
//    String sbi;
//    String bemp;
    private ArrayList<InfoList> list;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_list);

        listView = (ListView) findViewById(R.id.infoList);


        Log.e("listView", "adapter 初始化");
        adapter = new InfoAdapter(UBikeList.this, infoLists());

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
    }
public ArrayList<InfoList> infoLists(){
    MapsActivity main = new MapsActivity();
    list = new ArrayList<>();
    list = main.getList();
    return list;
}
//


}
