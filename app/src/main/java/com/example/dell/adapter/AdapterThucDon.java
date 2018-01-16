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
import com.example.dell.model.ThucPham;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Dell on 1/16/2018.
 */

public class AdapterThucDon extends ArrayAdapter<ThucPham> {

    Activity context;
    int resource;
    List<ThucPham> objects;

    public AdapterThucDon(@NonNull Activity context, int resource, @NonNull List<ThucPham> objects) {
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

        RoundedImageView imgThucPham = row.findViewById(R.id.imgThucDon);
        TextView txtTenThucPham = row.findViewById(R.id.txtTenThucPham);
        TextView txtGiaThucPham = row.findViewById(R.id.txtGiaThucPham);

        ThucPham thucPham = this.objects.get(position);

        txtTenThucPham.setText(thucPham.getTenThucPham());
        txtGiaThucPham.setText(thucPham.getGiaThucPham()+"VNĐ");
        imgThucPham.setImageResource(thucPham.getImgThucPham());
        return row;
    }
}
