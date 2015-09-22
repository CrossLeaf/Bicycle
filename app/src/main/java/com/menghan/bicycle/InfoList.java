package com.menghan.bicycle;

/**
 * Created by MengHan on 2015/9/7.
 */
public class InfoList {
    private String sna;
    private String ar;
    private String sbi;
    private String bemp;

    public InfoList(String sna, String ar, String sbi, String bemp){
        setAr(ar);
        setBemp(bemp);
        setSbi(sbi);
        setSna(sna);
    }
    public void setSna(String sna) {
        this.sna = sna;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    public void setBemp(String bemp) {
        this.bemp = bemp;
    }

    public String getSna() {
        return sna;
    }

    public String getAr() {
        return ar;
    }

    public String getSbi() {
        return sbi;
    }

    public String getBemp() {
        return bemp;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
