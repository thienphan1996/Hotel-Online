package com.example.dell.datphongkhachsanonline;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.model.BanAn;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class DatBan extends AppCompatActivity {

    BanAn banAn;
    TextView txtMaBanDatTruoc,txtLoaiBanDatTruoc,txtTenKhachHangDatTruoc;
    FancyButton btnHuyDatBan,btnDatBan;
    EditText edtGioDatBan,edtNgayDatBan,edtGhiChu;
    Calendar calendar;
    SimpleDateFormat spfTime,spfDate;

    SQLiteDatabase database = null;


    String maUser,tenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnHuyDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DatBan.this);
                builder.setMessage("Bạn có muốn đặt bàn số "+banAn.getMaBan().substring(1,banAn.getMaBan().length())+" không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maBan = banAn.getMaBan();
                        String loaiBan = banAn.getLoaiBan();
                        String maKhachHang = maUser;
                        String tenKhachHang = tenUser;
                        String gioDat = edtGioDatBan.getText().toString();
                        String ngayDat = edtNgayDatBan.getText().toString();
                        String ghiChu = edtGhiChu.getText().toString();
                        database.execSQL("DELETE FROM DatBan WHERE MaKhachHang='"+maKhachHang+"'");
                        database.execSQL("INSERT INTO DatBan VALUES(null,'"+maBan+"','"+loaiBan+"','"+maKhachHang+"','"+tenKhachHang+"','"+gioDat+"','"+ngayDat+"','"+ghiChu+"')");
                        Toast.makeText(getApplicationContext(),"Quý khách đã đặt trước bàn "+maBan.substring(1,maBan.length())+",cảm ơn quý khách!",Toast.LENGTH_LONG).show();
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

        edtGioDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gio = calendar.get(Calendar.HOUR_OF_DAY);
                int phut = calendar.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtGioDatBan.setText(hourOfDay+":"+minute);
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(DatBan.this,callback,gio,phut,true);
                dialog.show();
            }
        });
        edtNgayDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                int thang = calendar.get(Calendar.MONTH)+1;
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgayDatBan.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(DatBan.this,callback,nam,thang,ngay);
                dialog.show();
            }
        });
    }

    private void addControls() {
        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);
        calendar = Calendar.getInstance();
        spfDate = new SimpleDateFormat("dd/MM/yyyy");
        spfTime = new SimpleDateFormat("HH:mm");

        SharedPreferences sharedPreferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        maUser = sharedPreferences.getString("MAUSER","");

        txtTenKhachHangDatTruoc = findViewById(R.id.txtTenKhachHangDatTruoc);
        txtMaBanDatTruoc = findViewById(R.id.txtMaBanDatTruoc);
        txtLoaiBanDatTruoc = findViewById(R.id.txtLoaiBanDatTruoc);

        edtGioDatBan = findViewById(R.id.edtGioDatBan);
        edtNgayDatBan = findViewById(R.id.edtNgayDatBan);
        edtGhiChu = findViewById(R.id.edtGhiChuDatBan);
        edtGioDatBan.setText(spfTime.format(calendar.getTime()));
        edtNgayDatBan.setText(spfDate.format(calendar.getTime()));

        btnHuyDatBan = findViewById(R.id.btnTroLaiDatBan);
        btnDatBan = findViewById(R.id.btnDatBan);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLEBANAN");
        banAn = (BanAn) bundle.getSerializable("BANAN");

        txtMaBanDatTruoc.setText(banAn.getMaBan().substring(1,banAn.getMaBan().length()));
        txtLoaiBanDatTruoc.setText("Bàn "+banAn.getLoaiBan());
        Cursor cursor = database.rawQuery("SELECT * FROM KhachHang WHERE MaKhachHang='"+maUser+"'",null);
        cursor.moveToLast();
        tenUser = cursor.getString(1);
        txtTenKhachHangDatTruoc.setText(tenUser);
    }
}
