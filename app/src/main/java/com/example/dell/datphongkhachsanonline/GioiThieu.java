package com.example.dell.datphongkhachsanonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GioiThieu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
