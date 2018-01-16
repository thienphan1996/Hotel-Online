package com.example.dell.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.datphongkhachsanonline.R;
import com.example.dell.model.Phong;

import java.util.List;

/**
 * Created by Dell on 1/15/2018.
 */

public class AdapterPhong extends ArrayAdapter<Phong> {

    Activity context;
    int resource;
    List<Phong> objects;

    public AdapterPhong(@NonNull Activity context, int resource, @NonNull List<Phong> objects) {
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

        ImageView imgDoor = row.findViewById(R.id.imgDoor);
        TextView txtGiaPhong = row.findViewById(R.id.txtGiaPhong);
        TextView txtTrangThai = row.findViewById(R.id.txtTrangThai);
        TextView txtLoaiPhong = row.findViewById(R.id.txtLoaiPhong);
        TextView txtMaPhong = row.findViewById(R.id.txtMaPhong);
        txtMaPhong.setText(String.valueOf(position+1));

        imgDoor.setImageResource(R.drawable.hotel_close);
        txtTrangThai.setText("Bận");
        txtTrangThai.setTextColor(Color.parseColor("#D50000"));
        Phong phong = this.objects.get(position);
        if (phong.getTrangThai().equals("Trống"))
        {
            imgDoor.setImageResource(R.drawable.hotel_open);
            txtTrangThai.setText("Trống");
            txtTrangThai.setTextColor(Color.parseColor("#00C853"));
        }
        txtGiaPhong.setText(phong.getGiaPhong()+"VNĐ");
        txtLoaiPhong.setText("Phòng " + phong.getLoaiPhong());
        return row;
    }
}
