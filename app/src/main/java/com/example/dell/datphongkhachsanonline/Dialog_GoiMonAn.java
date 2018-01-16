package com.example.dell.datphongkhachsanonline;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class Dialog_GoiMonAn extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase database = null;

    ArrayList<String> arrayID = new ArrayList<>();
    int i = 0;
    Button btnBanCuaKhach;
    Button btnBan01,btnBan02,btnBan03,btnBan04,btnBan05,btnBan06,btnBan07,btnBan08,btnBan09,btnBan10,btnBan11;
    Button btnBan12,btnBan13,btnBan14,btnBan15,btnBan16,btnBan17,btnBan18;
    FancyButton btnHuyDatMon,btnDatMon;

    RoundedImageView imgDialogGoiMon;
    TextView txtTenGoiMon,txtGiaGoiMon;


    String maThucPham,tenThucPham,giaThucPham,maKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__goi_mon_an);
        setTitle("Gọi món ăn");

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        Intent intent = getIntent();
        maThucPham = intent.getStringExtra("MATHUCPHAM");
        tenThucPham = intent.getStringExtra("TENTHUCPHAM");
        giaThucPham = intent.getStringExtra("GIATHUCPHAM");
        maKhachHang = intent.getStringExtra("MAKHACHHANG");
        int imgDrawable = intent.getIntExtra("IMGTHUCPHAM",0);

        btnHuyDatMon = findViewById(R.id.btnHuyGoiMon);
        btnDatMon = findViewById(R.id.btnGoiMon);
        imgDialogGoiMon = findViewById(R.id.imgDialogGoiMon);
        txtGiaGoiMon = findViewById(R.id.txtGiaGoiMon);
        txtTenGoiMon = findViewById(R.id.txtTenGoiMon);

        imgDialogGoiMon.setImageResource(imgDrawable);
        txtGiaGoiMon.setText(giaThucPham+"VNĐ");
        txtTenGoiMon.setText(tenThucPham);

        btnBan01 = findViewById(R.id.btnBan01);
        btnBan02 = findViewById(R.id.btnBan02);
        btnBan03 = findViewById(R.id.btnBan03);
        btnBan04 = findViewById(R.id.btnBan04);
        btnBan05 = findViewById(R.id.btnBan05);
        btnBan06 = findViewById(R.id.btnBan06);
        btnBan07 = findViewById(R.id.btnBan07);
        btnBan08 = findViewById(R.id.btnBan08);
        btnBan09 = findViewById(R.id.btnBan09);
        btnBan10 = findViewById(R.id.btnBan10);
        btnBan11 = findViewById(R.id.btnBan11);
        btnBan12 = findViewById(R.id.btnBan12);
        btnBan13 = findViewById(R.id.btnBan13);
        btnBan14 = findViewById(R.id.btnBan14);
        btnBan15 = findViewById(R.id.btnBan15);
        btnBan16 = findViewById(R.id.btnBan16);
        btnBan17 = findViewById(R.id.btnBan17);
        btnBan18 = findViewById(R.id.btnBan18);

        btnBan01.setOnClickListener(this);
        btnBan02.setOnClickListener(this);
        btnBan03.setOnClickListener(this);
        btnBan04.setOnClickListener(this);
        btnBan05.setOnClickListener(this);
        btnBan06.setOnClickListener(this);
        btnBan07.setOnClickListener(this);
        btnBan08.setOnClickListener(this);
        btnBan09.setOnClickListener(this);
        btnBan10.setOnClickListener(this);
        btnBan11.setOnClickListener(this);
        btnBan12.setOnClickListener(this);
        btnBan13.setOnClickListener(this);
        btnBan14.setOnClickListener(this);
        btnBan15.setOnClickListener(this);
        btnBan16.setOnClickListener(this);
        btnBan17.setOnClickListener(this);
        btnBan18.setOnClickListener(this);

        btnHuyDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayID.size()>0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dialog_GoiMonAn.this);
                    builder.setCancelable(false);
                    builder.setMessage("Bạn có muốn gọi món"+tenThucPham+" không?");
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Button btnDuocChon = findViewById(Integer.parseInt(arrayID.get(arrayID.size()-1)));
                            String soBan = btnDuocChon.getText().toString();
                            database.execSQL("INSERT INTO GoiMon VALUES(null,'"+maThucPham+"','"+tenThucPham+"','"+giaThucPham+"','"+maKhachHang+"','"+soBan+"',null)");
                            Toast.makeText(Dialog_GoiMonAn.this,"Cảm ơn bạn đã gọi món",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Hãy chọn bàn hiện tại của bạn!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String ID = v.getId()+"";
        arrayID.add(ID);
        if (arrayID.size()>1)
        {
            btnBanCuaKhach = findViewById(Integer.parseInt(arrayID.get(i)));
            btnBanCuaKhach.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            i++;
            Button btnHienTai = findViewById(Integer.parseInt(arrayID.get(i)));
            btnHienTai.setBackgroundColor(Color.parseColor("#E65100"));
        }
        else
        {
            btnBanCuaKhach = findViewById(v.getId());
            btnBanCuaKhach.setBackgroundColor(Color.parseColor("#E65100"));
        }

    }
}
