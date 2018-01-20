package com.example.dell.datphongkhachsanonline;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dell.adapter.AdapterKhuyenMai;
import com.example.dell.model.KhuyenMai;

import java.util.ArrayList;

public class ManHinhKhuyenMai extends AppCompatActivity {

    ListView lvKhuyenMai;
    ArrayList<KhuyenMai> arrKhuyenMai;
    AdapterKhuyenMai adapterKhuyenMai;

    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        lvKhuyenMai = findViewById(R.id.lvKhuyenMai);
        arrKhuyenMai = new ArrayList<>();
        adapterKhuyenMai = new AdapterKhuyenMai(this,R.layout.listview_khuyenmai,arrKhuyenMai);
        Cursor cursor = database.rawQuery("SELECT * FROM KhuyenMai",null);
        int i = 0;
        int []imgKhuyenMai = new int[4];
        imgKhuyenMai[0] = R.drawable.khuyenmai_20;
        imgKhuyenMai[1] = R.drawable.vfp_hotel_sale;
        imgKhuyenMai[2] = R.drawable.khuyenmai_30;
        imgKhuyenMai[3] = R.drawable.khuyenmai_30_2;
        while (cursor.moveToNext())
        {
            KhuyenMai khuyenMai = new KhuyenMai();
            khuyenMai.setTenKhuyenMai(cursor.getString(0));
            khuyenMai.setTGBatDau(cursor.getString(1));
            khuyenMai.setTGKetThuc(cursor.getString(2));
            khuyenMai.setNoiDung(cursor.getString(3));
            khuyenMai.setImgKhuyenMai(imgKhuyenMai[i]);
            i++;
            arrKhuyenMai.add(khuyenMai);
        }
        lvKhuyenMai.setAdapter(adapterKhuyenMai);
    }
}
