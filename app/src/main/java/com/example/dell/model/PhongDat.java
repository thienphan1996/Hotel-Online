package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/17/2018.
 */

public class PhongDat implements Serializable {
    String maPhong;
    String maKhachHang;
    String tenKhachHang;
    String giaPhong;
    String gioDat;
    String ngayDat;

    public PhongDat() {
    }

    public PhongDat(String maPhong, String maKhachHang, String tenKhachHang, String giaPhong, String gioDat, String ngayDat) {

        this.maPhong = maPhong;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.giaPhong = giaPhong;
        this.gioDat = gioDat;
        this.ngayDat = ngayDat;
    }

    public String getMaPhong() {

        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getGioDat() {
        return gioDat;
    }

    public void setGioDat(String gioDat) {
        this.gioDat = gioDat;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }
}
