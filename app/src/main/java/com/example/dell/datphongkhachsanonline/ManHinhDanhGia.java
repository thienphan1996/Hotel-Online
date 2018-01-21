package com.example.dell.datphongkhachsanonline;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dell.adapter.AdapterDanhGia;
import com.example.dell.model.DanhGia;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class ManHinhDanhGia extends AppCompatActivity {

    SQLiteDatabase database = null;

    ArrayList<DanhGia> arrDanhGia;
    ListView lvDanhGia;
    AdapterDanhGia adapterDanhGia;

    EditText edtNoiDungDanhGia;
    RatingBar ratDanhGia;
    FancyButton btnGuiDanhGia;

    String maUser = "";
    String tenUser = "";

    boolean isDanhGia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_danh_gia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        SharedPreferences sharedPreferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        maUser = sharedPreferences.getString("MAUSER","");

        final Cursor cursor = database.rawQuery("SELECT * FROM KhachHang WHERE MaKhachHang='"+maUser+"'",null);
        cursor.moveToLast();
        tenUser = cursor.getString(1);

        edtNoiDungDanhGia = findViewById(R.id.edtNoiDungDanhGia);
        ratDanhGia = findViewById(R.id.ratDanhGia);
        btnGuiDanhGia = findViewById(R.id.btnGuiDanhGia);

        arrDanhGia = new ArrayList<>();
        lvDanhGia = findViewById(R.id.lvDanhGia);
        adapterDanhGia = new AdapterDanhGia(ManHinhDanhGia.this,R.layout.listview_danhgia,arrDanhGia);
        showAllDanhGiafromData();

        btnGuiDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float diem = ratDanhGia.getRating()*2;
                int diemDanhGia = (int) diem;
                String noiDung = edtNoiDungDanhGia.getText().toString();
                Cursor cursor1 = database.rawQuery("SELECT * FROM DanhGia WHERE MaKhachHang='"+maUser+"'",null);
                cursor1.moveToLast();
                if (cursor1.getCount()>0)
                {
                    isDanhGia = true;
                    Toast.makeText(ManHinhDanhGia.this,"Nội dung đánh giá của bạn đã tồn tại!",Toast.LENGTH_SHORT).show();
                }
                if (isDanhGia==false)
                {
                    database.execSQL("INSERT INTO DanhGia VALUES('"+maUser+"','"+tenUser+"',"+diemDanhGia+",'"+noiDung+"')");
                    Toast.makeText(ManHinhDanhGia.this,"Cảm ơn bạn đã đánh giá! ",Toast.LENGTH_SHORT).show();
                    DanhGia danhGia = new DanhGia(maUser,tenUser,diemDanhGia,noiDung);
                    arrDanhGia.add(danhGia);
                    adapterDanhGia.notifyDataSetChanged();
                    isDanhGia = true;
                }
            }
        });
    }

    private void showAllDanhGiafromData() {
        Cursor cursor = database.rawQuery("SELECT * FROM DanhGia",null);
        while (cursor.moveToNext())
        {
            DanhGia danhGia = new DanhGia();
            danhGia.setMaKhachHang(cursor.getString(0));
            danhGia.setTenKhachHang(cursor.getString(1));
            danhGia.setDiemDanhGia(cursor.getInt(2));
            danhGia.setNoiDung(cursor.getString(3));
            arrDanhGia.add(danhGia);
        }
        lvDanhGia.setAdapter(adapterDanhGia);
        adapterDanhGia.notifyDataSetChanged();
    }
}
