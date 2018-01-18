package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/18/2018.
 */

public class BanAn implements Serializable {
    String maBan;
    String loaiBan;
    String trangThai;

    public BanAn() {
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getLoaiBan() {
        return loaiBan;
    }

    public void setLoaiBan(String loaiBan) {
        this.loaiBan = loaiBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public BanAn(String maBan, String loaiBan, String trangThai) {

        this.maBan = maBan;
        this.loaiBan = loaiBan;
        this.trangThai = trangThai;
    }
}
