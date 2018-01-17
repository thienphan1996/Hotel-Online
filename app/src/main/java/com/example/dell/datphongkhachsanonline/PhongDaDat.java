package com.example.dell.datphongkhachsanonline;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class PhongDaDat extends AppCompatActivity {

    SQLiteDatabase database = null;

    TextView txtMaSoPhongDaDat,txtGiaPhongDaDat,txtThoiGianPhongDaDat;
    FancyButton btnTroLaiPhongDaDat,btnHuyDatPhong;

    String maPhong;
    boolean phonTonTai = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_da_dat);

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        txtMaSoPhongDaDat = findViewById(R.id.txtmaSoPhongDaDat);
        txtGiaPhongDaDat = findViewById(R.id.txtPhongDaDat_Gia);
        txtThoiGianPhongDaDat = findViewById(R.id.txtPhongDaDat_ThoiGian);
        btnHuyDatPhong = findViewById(R.id.btnHuyDatPhong);
        btnTroLaiPhongDaDat = findViewById(R.id.btnTroLaiDatPhong);



        SharedPreferences sharedPreferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        final String maUser = sharedPreferences.getString("MAUSER","");
        Cursor cursor = database.rawQuery("SELECT * FROM PhongDat WHERE MaKhachHang='"+maUser+"'",null);
        cursor.moveToLast();
        maPhong = cursor.getString(1);
        txtMaSoPhongDaDat.setText(maPhong.substring(2,maPhong.length()));
        txtGiaPhongDaDat.setText(cursor.getString(4)+"VNĐ");
        txtThoiGianPhongDaDat.setText(cursor.getString(5)+" ngày "+cursor.getString(6));

        btnTroLaiPhongDaDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHuyDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhongDaDat.this);
                builder.setMessage("Quý khách có muốn hủy đặt phòng "+maPhong.substring(2,maPhong.length())+" không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.execSQL("DELETE FROM PhongDat WHERE MaKhachHang='"+maUser+"'");
                        Toast.makeText(PhongDaDat.this,"Đã hủy,xin cảm ơn quý khách!",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
    }
}
