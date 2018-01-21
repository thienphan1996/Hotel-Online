package com.example.dell.datphongkhachsanonline;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class DoiMatKhau extends AppCompatActivity {

    EditText edtDoiMatKhauCu,edtDoiMatKhauMoi,edtDoiMatKhauNhapLai;
    FancyButton btnXacNhanDoiMatKhau;

    SQLiteDatabase database = null;

    String maUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        SharedPreferences sharedPreferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        maUser = sharedPreferences.getString("MAUSER","");

        edtDoiMatKhauCu = findViewById(R.id.edtDoiMatKhauCu);
        edtDoiMatKhauMoi = findViewById(R.id.edtDoiMatKhauMoi);
        edtDoiMatKhauNhapLai = findViewById(R.id.edtDoiMatKhauNhapLai);
        btnXacNhanDoiMatKhau = findViewById(R.id.btnXacNhanDoiMatKhau);

        btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.rawQuery("SELECT * FROM TaiKhoan WHERE MaUser='"+maUser+"'",null);
                cursor.moveToLast();
                String matKhauCu = cursor.getString(2);
                if (edtDoiMatKhauCu.getText().toString().equals(matKhauCu)==false)
                {
                    edtDoiMatKhauCu.setError("Mật khẩu cũ không đúng");
                }
                else if (edtDoiMatKhauMoi.getText().toString().equals(matKhauCu) || edtDoiMatKhauMoi.getText().toString().length()==0)
                {
                    edtDoiMatKhauMoi.setError("Mật khẩu mới không hợp lệ");
                }
                else if (edtDoiMatKhauMoi.getText().toString().equals(edtDoiMatKhauNhapLai.getText().toString())==false)
                {
                    edtDoiMatKhauNhapLai.setError("Nhập lại mật khẩu không khớp");
                }
                else
                {
                    String matKhauMoi = edtDoiMatKhauMoi.getText().toString();
                    database.execSQL("UPDATE TaiKhoan SET MatKhau='"+matKhauMoi+"' WHERE MaUser='"+maUser+"'");
                    Toast.makeText(getApplicationContext(),"Đã đổi mật khẩu thành công!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }


}
