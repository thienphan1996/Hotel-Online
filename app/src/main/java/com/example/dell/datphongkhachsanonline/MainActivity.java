package com.example.dell.datphongkhachsanonline;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.dell.adapter.AdapterPhong;
import com.example.dell.model.Phong;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase database=null;

    ViewFlipper vfpPanel;
    RelativeLayout rlViewFlipper,rlDiemTichLuy;

    ImageView imgVFPPre,imgVFPNext;
    TextView txtTenPerson,txtDiemTichLuy;

    float toadox1,toadox2;

    GridView gvListPhong;
    ArrayList<Phong> arrPhongTangTret;
    AdapterPhong adapterPhong;


    String maUser = new String();
    String tenUser = "";

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Khách sạn Online");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Dialog dialogThanhToan = new Dialog(MainActivity.this);
                dialogThanhToan.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogThanhToan.setContentView(R.layout.dialog_phongdadat);
                dialogThanhToan.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        txtTenPerson = view.findViewById(R.id.txtTenPerson);
        rlDiemTichLuy = view.findViewById(R.id.rlDiemTichLuy);
        txtDiemTichLuy = view.findViewById(R.id.txtDiemTichLuy);
        navigationView.setNavigationItemSelectedListener(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);
        vfpPanel = findViewById(R.id.vfpPanel);
        vfpPanel.setInAnimation(getApplicationContext(),android.R.anim.slide_in_left);
        vfpPanel.setOutAnimation(getApplicationContext(),R.anim.out);
        vfpPanel.setAutoStart(true);
        vfpPanel.setFlipInterval(4000);
        rlViewFlipper = findViewById(R.id.rlViewFlipper);
        imgVFPPre = findViewById(R.id.imgVFPPre);
        imgVFPNext = findViewById(R.id.imgVFPNext);

        Intent intent = getIntent();
        maUser = intent.getStringExtra("MAUSER");

        int diemTichLuy = 0;
        arrPhongTangTret = new ArrayList<>();
        gvListPhong = findViewById(R.id.gvTangTret);
        adapterPhong = new AdapterPhong(MainActivity.this,R.layout.gridview_content,arrPhongTangTret);
        ShowAllDataFromDB();


        String sub = maUser.substring(0,2);
        if (sub.equals("NV"))
        {
            Cursor cursor = database.rawQuery("SELECT * FROM NhanVien WHERE MaNhanVien='"+maUser+"'",null);
            while (cursor.moveToNext())
            {
                tenUser += cursor.getString(1);
            }
            txtTenPerson.setText(tenUser);
            rlDiemTichLuy.setVisibility(View.GONE);
        }
        else
        {
            Cursor cursor = database.rawQuery("SELECT * FROM KhachHang WHERE MaKhachHang='"+maUser+"'",null);
            while (cursor.moveToNext())
            {
                tenUser += cursor.getString(1);
                diemTichLuy = cursor.getInt(2);
            }
            txtDiemTichLuy.setText(String.valueOf(diemTichLuy));
            txtTenPerson.setText(tenUser);
        }
    }



    private void ShowAllDataFromDB() {
        Cursor cursor = database.rawQuery("SELECT * FROM Phong",null);
        while (cursor.moveToNext())
        {
            Phong phong = new Phong();
            phong.setLoaiPhong(cursor.getString(1));
            phong.setGiaPhong(cursor.getString(2));
            phong.setTrangThai(cursor.getString(3));
            arrPhongTangTret.add(phong);
        }
        adapterPhong.notifyDataSetChanged();
        gvListPhong.setAdapter(adapterPhong);
    }

    private void addEvents() {
        rlViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        toadox1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        toadox2 = motionEvent.getX();
                        if (toadox1 > toadox2)
                        {
                            vfpAnimationRight();
                            vfpPanel.showNext();
                        }
                        else if (toadox1 < toadox2)
                        {
                            vfpAnimationLeft();
                            vfpPanel.showPrevious();
                        }
                        break;
                }
                return true;
            }
        });

        imgVFPPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vfpAnimationLeft();
                vfpPanel.showPrevious();
            }
        });

        imgVFPNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vfpAnimationRight();
                vfpPanel.showNext();
            }
        });

        gvListPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_datphong);
                ImageView imgDialogDatPhong = dialog.findViewById(R.id.imgDialogDatPhong);
                TextView txtDialogLoaiPhong = dialog.findViewById(R.id.txtDialogLoaiPhong);
                TextView txtDialogGia = dialog.findViewById(R.id.txtDialogGia);
                TextView txtDialogGiaDem = dialog.findViewById(R.id.txtDialogGiaDem);
                TextView txtDialogGiaNgay = dialog.findViewById(R.id.txtDialogGiaNgay);
                final TextView txtDialogTrangThai = dialog.findViewById(R.id.txtDialogTrangThai);
                FancyButton btnDialogHuy = dialog.findViewById(R.id.btnHuyDialogDatPhong);
                FancyButton btnDialoaDatPhong = dialog.findViewById(R.id.btnDialogDatPhong);


                txtDialogLoaiPhong.setText("Loại: Phòng "+arrPhongTangTret.get(i).getLoaiPhong());
                txtDialogGia.setText("Giá: "+arrPhongTangTret.get(i).getGiaPhong()+"đ/giờ");
                txtDialogGiaDem.setText("Giá theo đêm: "+String.valueOf(Integer.parseInt(arrPhongTangTret.get(i).getGiaPhong())*3)+"đ/đêm");
                txtDialogGiaNgay.setText("Giá theo ngày: "+String.valueOf(Integer.parseInt(arrPhongTangTret.get(i).getGiaPhong())*4)+"đ/ngày");
                txtDialogTrangThai.setText(arrPhongTangTret.get(i).getTrangThai());
                if (arrPhongTangTret.get(i).getTrangThai().equals("Trống"))
                {
                    txtDialogTrangThai.setTextColor(Color.parseColor("#64DD17"));
                }
                if (arrPhongTangTret.get(i).getLoaiPhong().equals("Lạnh"))
                {
                    imgDialogDatPhong.setImageResource(R.drawable.hotel_room_vip);
                }

                btnDialogHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                btnDialoaDatPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDialogTrangThai.getText().toString().equals("Đã có người đặt"))
                        {
                            Toast.makeText(MainActivity.this,"Đã có người đặt,mời bạn chọn phòng khác!",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Intent intent = new Intent(MainActivity.this,DatPhong.class);
                            intent.putExtra("MAKHACHHANG",maUser);
                            Bundle bundle = new Bundle();
                            Phong phong = arrPhongTangTret.get(index);
                            bundle.putSerializable("PHONG",phong);
                            intent.putExtra("MYBUNDLE",bundle);
                            startActivity(intent);
                            dialog.cancel();
                        }

                    }
                });
                dialog.show();
            }
        });



    }



    private void vfpAnimationLeft() {
        vfpPanel.setInAnimation(this,android.R.anim.slide_in_left);
    }

    private void vfpAnimationRight() {
        vfpPanel.setInAnimation(this,R.anim.in);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnu_XemPhong) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_KhuyenMai) {
            // Handle the camera action
        } else if (id == R.id.nav_DanhGia) {

        } else if (id == R.id.nav_DoiMatKhau) {

        } else if (id == R.id.nav_DangXuat) {

        } else if (id == R.id.nav_GioiThieu) {

        } else if (id == R.id.nav_LienHe) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
