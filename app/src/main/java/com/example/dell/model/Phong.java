package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/15/2018.
 */

public class Phong implements Serializable{
    String maPhong;
    String loaiPhong;
    String giaPhong;
    String trangThai;

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Phong() {

    }

    public Phong(String maPhong, String loaiPhong, String giaPhong, String trangThai) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.trangThai = trangThai;
    }
}
