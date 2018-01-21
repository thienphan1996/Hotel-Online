package com.example.dell.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.datphongkhachsanonline.R;
import com.example.dell.model.DanhGia;

import java.util.List;

/**
 * Created by Dell on 1/21/2018.
 */

public class AdapterDanhGia extends ArrayAdapter<DanhGia> {
    Activity context;
    int resource;
    List<DanhGia> objects;
    public AdapterDanhGia(@NonNull Activity context, int resource, @NonNull List<DanhGia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtTenKhachHangDanhGia = row.findViewById(R.id.txtTenNguoiDanhGia);
        TextView txtDiemNguoiDanhGia = row.findViewById(R.id.txtDiemNguoiDanhGia);
        TextView txtNoiDungNguoiDanhGia = row.findViewById(R.id.txtNoiDungNguoiDanhGia);

        DanhGia danhGia = this.objects.get(position);

        txtTenKhachHangDanhGia.setText(danhGia.getTenKhachHang());
        txtDiemNguoiDanhGia.setText(danhGia.getDiemDanhGia()+"/10");
        txtNoiDungNguoiDanhGia.setText(danhGia.getNoiDung());
        return row;
    }
}
