package com.example.dell.datphongkhachsanonline;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import mehdi.sakout.fancybuttons.FancyButton;

public class Login extends AppCompatActivity {

    EditText edtTaiKhoan,edtMatKhau;
    FancyButton btnDangNhap,btnDangKy;
    CheckBox chkNhoMatKhau;

    String DATABASE_NAME = "dbKhachSan.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        XuLySaoChepCSDLTuAssetsVaoHeThong();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);


        addControls();
        addEvents();
    }

    @Override
    protected void onPause() {
        SharedPreferences preferences = getSharedPreferences("nhodangnhap",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",edtTaiKhoan.getText().toString());
        editor.putString("password",edtMatKhau.getText().toString());
        editor.putBoolean("checked",chkNhoMatKhau.isChecked());
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("nhodangnhap",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("checked",false))
        {
            edtTaiKhoan.setText(sharedPreferences.getString("user",""));
            edtMatKhau.setText(sharedPreferences.getString("password",""));
            chkNhoMatKhau.setChecked(true);
        }
        super.onResume();
    }

    private void addEvents() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Login.this);
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_dangky);

                final EditText edtTaiKhoanDangKy = dialog.findViewById(R.id.edtTaiKhoanDangKy);
                final EditText edtMatKhauDangKy = dialog.findViewById(R.id.edtMatkhauDangKy);
                final EditText edtNhapLaiMatKhau = dialog.findViewById(R.id.edtNhapLaiMatKhau);
                final EditText edtHotenDangKy = dialog.findViewById(R.id.edtHoTenDangKy);
                final EditText edtNamSinh = dialog.findViewById(R.id.edtNamSinh);
                final RadioButton radNam = dialog.findViewById(R.id.radNam);
                RadioButton radNu = dialog.findViewById(R.id.radNu);
                FancyButton btnXacNhanDangKy = dialog.findViewById(R.id.btnXacNhanDangKy);
                FancyButton btnHuyDangKy = dialog.findViewById(R.id.btnHuyDangKy);

                btnHuyDangKy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                btnXacNhanDangKy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String taiKhoanMoi = edtTaiKhoanDangKy.getText().toString();
                        String matKhauMoi = edtMatKhauDangKy.getText().toString();
                        String hoTenMoi = edtHotenDangKy.getText().toString();
                        String namSinh = edtNamSinh.getText().toString();
                        String gioiTinh = "Nữ";
                        if (radNam.isChecked())
                        {
                            gioiTinh = "Nam";
                        }
                        Cursor cursor = database.rawQuery("SELECT * FROM TaiKhoan",null);
                        int soPhanTu = cursor.getCount()+1;
                        String idKhachHang = "KH" + String.valueOf(soPhanTu);
                        while (cursor.moveToNext())
                        {
                            if (edtTaiKhoanDangKy.getText().toString().equals(cursor.getString(1)))
                            {
                                edtTaiKhoanDangKy.setError("Tên tài khoản đã tồn tại");
                            }
                        }
                        if (taiKhoanMoi.length()==0)
                        {
                            edtTaiKhoanDangKy.setError("Tài khoản không được trống");
                        }
                        else if (matKhauMoi.length()==0)
                        {
                            edtMatKhauDangKy.setError("Mật khẩu không được trống");
                        }
                        else if (edtMatKhauDangKy.getText().toString().equals(edtNhapLaiMatKhau.getText().toString())==false)
                        {
                            edtNhapLaiMatKhau.setError("Mật khẩu không khớp!");
                        }
                        else if (hoTenMoi.length()==0)
                        {
                            edtHotenDangKy.setError("Tên không được trống");
                        }
                        else if (namSinh.length()==0)
                        {
                            edtNamSinh.setError("Năm sinh không được trống");
                        }
                        else
                        {
                            database.execSQL("INSERT INTO TaiKhoan VALUES (null,'"+taiKhoanMoi+"','"+matKhauMoi+"','"+idKhachHang+"')");
                            database.execSQL("INSERT INTO KhachHang (MaKhachHang,TenKhachHang,GioiTinh,NamSinh) VALUES ('"+ idKhachHang +"','" + hoTenMoi + "','" + hoTenMoi + "','" + namSinh +"')");
                            Toast.makeText(Login.this,"Đăng ký tài khoản thành công!",Toast.LENGTH_SHORT).show();
                            edtTaiKhoan.setText(taiKhoanMoi);
                            edtMatKhau.setText(matKhauMoi);
                            dialog.cancel();
                        }
                    }
                });

                dialog.show();

            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });
    }

    private void xuLyDangNhap() {
        String taiKhoan = edtTaiKhoan.getText().toString();
        String matKhau= edtMatKhau.getText().toString();
        boolean kiemTra = false;
        Cursor cursor = database.rawQuery("SELECT * FROM TaiKhoan",null);
        while (cursor.moveToNext())
        {
            String taikhoan = cursor.getString(1);
            String matkhau = cursor.getString(2);
            if (taiKhoan.equals(taikhoan)==true && matKhau.equals(matkhau)==true)
            {
                kiemTra = true;
                Intent intent = new Intent(Login.this,MainActivity.class);
                String maUser = cursor.getString(3);
                intent.putExtra("MAUSER",maUser);
                startActivity(intent);
            }
        }
        if (kiemTra==false)
        {
            Toast.makeText(this,"Tài khoản hoặc mật khẩu không đúng!",Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkNhoMatKhau = findViewById(R.id.chkNhoMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }
    private void XuLySaoChepCSDLTuAssetsVaoHeThong() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if (!f.exists())
            {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("Loi_SaoChep",ex.toString());
        }
    }

    private String layDuongDanLuuTru()
    {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}
