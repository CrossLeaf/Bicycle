package com.menghan.bicycle;

/**
 * Created by MengHan on 2015/8/29.
 */
public class RetVal {
    private String iid;
    private String stationNo;
    private String sna;
    private String sip;
    private String tot;
    private String sarea;
    private String lat;
    private String lng;
    private String ar;
    private String sareaen;
    private String snaen;
    private String aren;
    private String avalibleBike;
    private String emptySpace;
    private String statusDesc;
    private String updateTime;

    public RetVal(String lat, String lng){
        setLat(lat);
        setLng(lng);
    }

    public String getIid() {
        return iid;
    }

    public String getStationNo() {
        return stationNo;
    }

    public String getSna() {
        return sna;
    }

    public String getSip() {
        return sip;
    }

    public String getTot() {
        return tot;
    }

    public String getSarea() {
        return sarea;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getAr() {
        return ar;
    }

    public String getSareaen() {
        return sareaen;
    }

    public String getSnaen() {
        return snaen;
    }

    public String getAren() {
        return aren;
    }

    public String getAvalibleBike() {
        return avalibleBike;
    }

    public String getEmptySpace() {
        return emptySpace;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public void setSna(String sna) {
        this.sna = sna;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public void setSareaen(String sareaen) {
        this.sareaen = sareaen;
    }

    public void setSnaen(String snaen) {
        this.snaen = snaen;
    }

    public void setAren(String aren) {
        this.aren = aren;
    }

    public void setAvalibleBike(String avalibleBike) {
        this.avalibleBike = avalibleBike;
    }

    public void setEmptySpace(String emptySpace) {
        this.emptySpace = emptySpace;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RetVal {" + "Lat=" + lat + ", Lng=" + lng +"}";
    }
}
