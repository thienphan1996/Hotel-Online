package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/21/2018.
 */

public class DanhGia implements Serializable{
    String maKhachHang;
    String tenKhachHang;
    int diemDanhGia;
    String noiDung;

    public DanhGia() {
    }

    public DanhGia(String maKhachHang, String tenKhachHang, int diemDanhGia, String noiDung) {

        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diemDanhGia = diemDanhGia;
        this.noiDung = noiDung;
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

    public int getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(int diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
