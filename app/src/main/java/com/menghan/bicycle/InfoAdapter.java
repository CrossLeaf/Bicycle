package com.menghan.bicycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MengHan on 2015/8/29.
 */
public class InfoAdapter extends BaseAdapter {

    // 定義 LayoutInflater
    private LayoutInflater myInflater;
    // 定義 Adapter 內藴藏的資料容器
    private ArrayList<InfoList> list;

    public InfoAdapter(Context context, ArrayList<InfoList> list){  //建構子為了動態初始化
        //預先取得 LayoutInflater 物件實體
        myInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() { // 公定寫法(取得List資料筆數)
        return list.size();
    }

    @Override
    public Object getItem(int position) {   // 公定寫法(取得該筆資料)
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {   // 公定寫法(取得該筆資料的position)
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            // 1:將 R.layout.cus_view 實例化
            convertView = myInflater.inflate(R.layout.inflate_row, null);

            // 2:建立 UI 標籤結構並存放到 holder
            holder = new ViewHolder();
            holder.sna = (TextView) convertView.findViewById(R.id.sna);
            holder.ar = (TextView)convertView.findViewById(R.id.ar);
            holder.sbi = (TextView)convertView.findViewById(R.id.sbi);
            holder.bemp = (TextView) convertView.findViewById(R.id.bemp);

            // 3:注入 UI 標籤結構 --> convertView
            convertView.setTag(holder);

        } else {
            // 取得  UI 標籤結構
            holder = (ViewHolder)convertView.getTag();
        }

        // 4:取得retVal物件資料
        InfoList infoList = list.get(position);

        // 5:設定顯示資料
        holder.sna.setText(infoList.getSna());
        holder.ar.setText (infoList.getAr());
        holder.sbi.setText(infoList.getSbi());
        holder.bemp.setText(infoList.getBemp());
        return convertView;
    }
    // UI 標籤結構
    static class ViewHolder {
        TextView sna;
        TextView ar;
        TextView sbi;
        TextView bemp;
    }
}
