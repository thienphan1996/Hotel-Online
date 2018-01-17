package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/17/2018.
 */

public class GoiMon implements Serializable {
    String maThucPham;
    String tenThucPham;
    String giaThucPham;
    String maKhachHang;
    String maBan;
    String soLuong;

    public GoiMon() {
    }

    public GoiMon(String maThucPham, String tenThucPham, String giaThucPham, String maKhachHang, String maBan, String soLuong) {

        this.maThucPham = maThucPham;
        this.tenThucPham = tenThucPham;
        this.giaThucPham = giaThucPham;
        this.maKhachHang = maKhachHang;
        this.maBan = maBan;
        this.soLuong = soLuong;
    }

    public String getMaThucPham() {

        return maThucPham;
    }

    public void setMaThucPham(String maThucPham) {
        this.maThucPham = maThucPham;
    }

    public String getTenThucPham() {
        return tenThucPham;
    }

    public void setTenThucPham(String tenThucPham) {
        this.tenThucPham = tenThucPham;
    }

    public String getGiaThucPham() {
        return giaThucPham;
    }

    public void setGiaThucPham(String giaThucPham) {
        this.giaThucPham = giaThucPham;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}
