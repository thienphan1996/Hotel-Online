package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/20/2018.
 */

public class KhuyenMai implements Serializable {
    String tenKhuyenMai;
    String TGBatDau;
    String TGKetThuc;
    String noiDung;
    int imgKhuyenMai;

    public int getImgKhuyenMai() {
        return imgKhuyenMai;
    }

    public void setImgKhuyenMai(int imgKhuyenMai) {
        this.imgKhuyenMai = imgKhuyenMai;
    }

    public KhuyenMai() {
    }

    public KhuyenMai(String tenKhuyenMai, String TGBatDau, String TGKetThuc, String noiDung, int imgKhuyenMai) {

        this.tenKhuyenMai = tenKhuyenMai;
        this.TGBatDau = TGBatDau;
        this.TGKetThuc = TGKetThuc;
        this.noiDung = noiDung;
        this.imgKhuyenMai = imgKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public String getTGBatDau() {
        return TGBatDau;
    }

    public void setTGBatDau(String TGBatDau) {
        this.TGBatDau = TGBatDau;
    }

    public String getTGKetThuc() {
        return TGKetThuc;
    }

    public void setTGKetThuc(String TGKetThuc) {
        this.TGKetThuc = TGKetThuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
