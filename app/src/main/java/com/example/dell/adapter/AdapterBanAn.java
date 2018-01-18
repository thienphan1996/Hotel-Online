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
import com.example.dell.model.BanAn;

import java.util.List;

/**
 * Created by Dell on 1/18/2018.
 */

public class AdapterBanAn extends ArrayAdapter<BanAn> {

    Activity context;
    int resource;
    List<BanAn> objects;

    public AdapterBanAn(@NonNull Activity context, int resource, @NonNull List<BanAn> objects) {
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

        TextView txtBanAnMaBan = row.findViewById(R.id.txtBanAnMaBan);
        ImageView imgTable = row.findViewById(R.id.imgTable);
        TextView txtBanAnLoaiBan = row.findViewById(R.id.txtBanAnLoai);
        TextView txtBanAnTrangThai = row.findViewById(R.id.txtBanAnTrangThai);

        BanAn banAn = this.objects.get(position);
        txtBanAnMaBan.setText(banAn.getMaBan().substring(1,banAn.getMaBan().length()));
        txtBanAnLoaiBan.setText("Bàn "+banAn.getLoaiBan());
        txtBanAnTrangThai.setText(banAn.getTrangThai());
        txtBanAnTrangThai.setTextColor(Color.parseColor("#D50000"));
        imgTable.setImageResource(R.drawable.table_clear);
        if (banAn.getTrangThai().equals("Trống"))
        {
            imgTable.setImageResource(R.drawable.table_close);
            txtBanAnTrangThai.setTextColor(Color.parseColor("#00C853"));
        }

        return row;
    }
}
