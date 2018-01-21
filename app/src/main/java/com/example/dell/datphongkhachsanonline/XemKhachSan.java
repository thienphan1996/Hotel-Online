package com.example.dell.datphongkhachsanonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class XemKhachSan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_khach_san);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
