package com.example.dell.model;

import java.io.Serializable;

/**
 * Created by Dell on 1/16/2018.
 */

public class ThucPham implements Serializable {
    String maThucPham;
    String tenThucPham;
    String giaThucPham;
    String loaiThucPham;
    int imgThucPham;

    public int getImgThucPham() {
        return imgThucPham;
    }

    public void setImgThucPham(int imgThucPham) {
        this.imgThucPham = imgThucPham;
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

    public String getLoaiThucPham() {
        return loaiThucPham;
    }

    public void setLoaiThucPham(String loaiThucPham) {
        this.loaiThucPham = loaiThucPham;
    }

    public ThucPham(String maThucPham, String tenThucPham, String giaThucPham, String loaiThucPham, int imgThucPham) {
        this.maThucPham = maThucPham;
        this.tenThucPham = tenThucPham;
        this.giaThucPham = giaThucPham;
        this.loaiThucPham = loaiThucPham;
        this.imgThucPham = imgThucPham;
    }

    public ThucPham() {
    }
}
