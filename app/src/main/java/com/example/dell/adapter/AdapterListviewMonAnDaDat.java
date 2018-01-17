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
import com.example.dell.model.GoiMon;

import java.util.List;

/**
 * Created by Dell on 1/17/2018.
 */

public class AdapterListviewMonAnDaDat extends ArrayAdapter<GoiMon> {

    Activity context;
    int resource;
    List<GoiMon> objects;

    public AdapterListviewMonAnDaDat(@NonNull Activity context, int resource, @NonNull List<GoiMon> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource= resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtLvTenMonAn = row.findViewById(R.id.txtLvTenMonAn);
        TextView txtLvGiaMonAn = row.findViewById(R.id.txtLvGiaMonAn);
        TextView txtLvSoluong = row.findViewById(R.id.txtLvSoLuong);
        TextView txtLvSoBan = row.findViewById(R.id.txtLvSoBan);

        GoiMon mon = this.objects.get(position);

        txtLvTenMonAn.setText((position+1)+"/ "+mon.getTenThucPham());
        txtLvGiaMonAn.setText(mon.getGiaThucPham()+"VNĐ");
        txtLvSoBan.setText(mon.getMaBan().substring(1,mon.getMaBan().length()));
        txtLvSoluong.setText(mon.getSoLuong());

        return row;
    }
}
