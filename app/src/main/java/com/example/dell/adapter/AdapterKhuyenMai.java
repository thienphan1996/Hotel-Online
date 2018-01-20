package com.example.dell.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.datphongkhachsanonline.R;
import com.example.dell.model.KhuyenMai;

import java.util.List;

/**
 * Created by Dell on 1/20/2018.
 */

public class AdapterKhuyenMai extends ArrayAdapter<KhuyenMai> {
    Activity context;
    int resource;
    List<KhuyenMai> objects;
    public AdapterKhuyenMai(@NonNull Activity context, int resource, @NonNull List<KhuyenMai> objects) {
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

        ImageView imgKhuyenMai = row.findViewById(R.id.imgKhuyenMai);
        TextView txtTenKhuyenMai = row.findViewById(R.id.txtTenKhuyenMai);
        TextView txtNoiDungKhuyenMai = row.findViewById(R.id.txtNoiDungKhuyenMai);
        TextView txtThoiGianKhuyenMai = row.findViewById(R.id.txtThoiGianKhuyenMai);



        KhuyenMai khuyenMai = this.objects.get(position);
        imgKhuyenMai.setImageResource(khuyenMai.getImgKhuyenMai());
        txtTenKhuyenMai.setText(khuyenMai.getTenKhuyenMai());
        txtNoiDungKhuyenMai.setText(khuyenMai.getNoiDung());
        txtThoiGianKhuyenMai.setText(khuyenMai.getTGBatDau().substring(0,5)+"-"+khuyenMai.getTGKetThuc());
        return row;
    }
}
