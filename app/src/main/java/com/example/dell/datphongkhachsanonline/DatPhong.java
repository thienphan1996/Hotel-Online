package com.example.dell.datphongkhachsanonline;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.model.Phong;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class DatPhong extends AppCompatActivity {

    RadioButton radGia,radGiaDem,radGiaNgay;
    FancyButton btnHuyDatPhong,btnXacNhanDatPhong;
    EditText edtGioDat,edtNgayDat;

    SQLiteDatabase database = null;

    String maKhachHang = new String();

    Phong phong = new Phong();
    Calendar now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_phong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addControls() {
        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);
        radGia = findViewById(R.id.radGiaPhong);
        radGiaDem = findViewById(R.id.radGiaPhongDem);
        radGiaNgay = findViewById(R.id.radGiaPhongNgay);
        btnHuyDatPhong = findViewById(R.id.btnHuyDatPhong);
        btnXacNhanDatPhong = findViewById(R.id.btnDatPhong);
        edtGioDat = findViewById(R.id.edtGioDat);
        edtNgayDat = findViewById(R.id.edtNgayDat);

        Intent intent = getIntent();
        maKhachHang = intent.getStringExtra("MAKHACHHANG");
        Bundle bundle = intent.getBundleExtra("MYBUNDLE");
        phong = (Phong) bundle.getSerializable("PHONG");
        radGia.setText(phong.getGiaPhong()+"đ/giờ");
        radGiaDem.setText(Integer.parseInt(phong.getGiaPhong())*3+"đ/đêm");
        radGiaNgay.setText(Integer.parseInt(phong.getGiaPhong())*4+"đ/ngày");

        now = Calendar.getInstance();
        String strTimeFormat24 = "HH:mm";
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf =null;
        sdf = new SimpleDateFormat(strTimeFormat24);
        edtGioDat.setText(sdf.format(now.getTime()));
        SimpleDateFormat sdf1 = null;
        sdf1 = new SimpleDateFormat(strDateFormat);
        edtNgayDat.setText(sdf1.format(now.getTime()));
    }

    private void addEvents() {
        btnHuyDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtGioDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gio = now.get(Calendar.HOUR_OF_DAY);
                int phut = now.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        edtGioDat.setText(i+":"+i1);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(DatPhong.this,callback,gio,phut,true);
                timePickerDialog.show();
            }
        });
        edtNgayDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ngay = now.get(Calendar.DAY_OF_MONTH);
                int thang = now.get(Calendar.MONTH);
                int nam = now.get(Calendar.YEAR);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        edtNgayDat.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(DatPhong.this,callback,nam,thang,ngay);
                datePickerDialog.show();
            }
        });

        btnXacNhanDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = phong.getMaPhong();
                String giaPhong = new String();
                String tenKhachHang = new String();
                String gioDat = edtGioDat.getText().toString();
                String ngayDat = edtNgayDat.getText().toString();
                 if (radGia.isChecked())
                 {
                     giaPhong = phong.getGiaPhong();
                 }
                if (radGiaDem.isChecked())
                {
                    giaPhong = String.valueOf(Integer.parseInt(phong.getGiaPhong())*3);
                }
                if (radGiaNgay.isChecked())
                {
                    giaPhong = String.valueOf(Integer.parseInt(phong.getGiaPhong())*4);
                }
                Cursor cursor = database.rawQuery("SELECT * FROM KhachHang WHERE MaKhachHang='"+maKhachHang+"'",null);
                 while (cursor.moveToNext())
                 {
                     tenKhachHang += cursor.getString(1);
                 }
                 database.execSQL("DELETE FROM PhongDat WHERE MaKhachHang='"+maKhachHang+"'");
                 database.execSQL("INSERT INTO PhongDat VALUES (null,'"+maPhong+"','"+maKhachHang+"','"+tenKhachHang+"','"+giaPhong+"','"+gioDat+"','"+ngayDat+"')");
                 Toast.makeText(DatPhong.this,"Đã đặt phòng thành công!",Toast.LENGTH_SHORT).show();
                 finish();
            }
        });
    }
}
