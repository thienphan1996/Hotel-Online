package com.example.dell.datphongkhachsanonline;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class DoiDiem extends AppCompatActivity {

    TextView txtDiemCuaBan;
    RadioButton radDoiDiem1,radDoiDiem2,radDoiDiem3;

    FancyButton btnHuyDoiDiem,btnDoiDiem;

    String maUser;

    SQLiteDatabase database = null;
    int diemMuonDoi = 0;
    int diemTichLuy = 0;
    String giaTriDoi = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_diem);

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        txtDiemCuaBan = findViewById(R.id.txtDiemCuaBan);
        radDoiDiem1 = findViewById(R.id.radDoiDiem1);
        radDoiDiem2 = findViewById(R.id.radDoiDiem2);
        radDoiDiem3 = findViewById(R.id.radDoiDiem3);

        btnHuyDoiDiem = findViewById(R.id.btnHuyDoiDiem);
        btnDoiDiem = findViewById(R.id.btnDoiDiem);
        SharedPreferences preferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        maUser = preferences.getString("MAUSER","");
        Cursor cursor = database.rawQuery("SELECT * FROM KhachHang WHERE MaKhachHang='"+maUser+"'",null);
        cursor.moveToLast();
        diemTichLuy = cursor.getInt(2);



        txtDiemCuaBan.setText(String.valueOf(diemTichLuy));

        btnHuyDoiDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDoiDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radDoiDiem1.isChecked()){
                    diemMuonDoi = 50;
                    giaTriDoi = "25k";
                }
                if (radDoiDiem2.isChecked()){
                    diemMuonDoi = 100;
                    giaTriDoi = "50k";
                }
                if (radDoiDiem3.isChecked()){
                    diemMuonDoi = 150;
                    giaTriDoi = "75k";
                }
                if (diemMuonDoi>diemTichLuy)
                {
                    Toast.makeText(DoiDiem.this,"Bạn không đủ điểm!",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoiDiem.this);
                    builder.setMessage("Bạn có muốn đổi "+diemMuonDoi+" điểm để lấy mã giảm "+giaTriDoi+" không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            diemTichLuy = diemTichLuy-diemMuonDoi;
                            database.execSQL("UPDATE KhachHang SET DiemTichLuy="+diemTichLuy+" WHERE MaKhachHang='"+maUser+"'");
                            String maGiamGia = maUser+diemMuonDoi+giaTriDoi+"2018";
                            database.execSQL("INSERT INTO MaGiamGia VALUES (null,'"+maUser+"','"+maGiamGia+"','"+giaTriDoi+"')");
                            Toast.makeText(DoiDiem.this,"Mã giảm giá của bạn là: "+maGiamGia+"\nMã có hiệu lực trong 30 ngày",Toast.LENGTH_LONG).show();
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
            }
        });
    }
}
