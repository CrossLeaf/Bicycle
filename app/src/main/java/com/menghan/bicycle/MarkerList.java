package com.menghan.bicycle;

/**
 * Created by Hello on 2015/9/22.
 */
public class MarkerList {
    private String sna;
    private String sbi;
    private String bemp;
    private double lat;
    private double lng;

    public MarkerList(String sna, String sbi, String bemp, String lat, String lng){
        setBemp(bemp);
        setSbi(sbi);
        setSna(sna);
        setLat(lat);
        setLng(lng);
    }
    public void setSna(String sna) {
        this.sna = sna;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    public void setBemp(String bemp) {
        this.bemp = bemp;
    }

    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    public void setLng(String lng) {
        this.lng = Double.parseDouble(lng);
    }


    public String getSna() {
        return sna;
    }

    public String getSbi() {
        return sbi;
    }

    public String getBemp() {
        return bemp;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
