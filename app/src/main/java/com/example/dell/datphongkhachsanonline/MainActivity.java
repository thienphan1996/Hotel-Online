package com.example.dell.datphongkhachsanonline;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.dell.adapter.AdapterBanAn;
import com.example.dell.adapter.AdapterPhong;
import com.example.dell.adapter.AdapterThucDon;
import com.example.dell.model.BanAn;
import com.example.dell.model.Phong;
import com.example.dell.model.ThucPham;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase database=null;

    ViewFlipper vfpPanel;
    RelativeLayout rlViewFlipper,rlDiemTichLuy;
    LinearLayout lnDatTruoc;

    ImageView imgVFPPre,imgVFPNext;
    TextView txtTenPerson,txtDiemTichLuy;
    FancyButton btnClickPhong,btnClickThucDon;

    float toadox1,toadox2;

    GridView gvListPhong;
    ArrayList<Phong> arrPhongTangTret;
    AdapterPhong adapterPhong;

    GridView gvThucDon;
    ArrayList<ThucPham> arrThucDon;
    AdapterThucDon adapterThucDon;

    GridView gvBanAn;
    ArrayList<BanAn> arrBanAn;
    AdapterBanAn adapterBanAn;

    String maUser = new String();
    String tenUser = "";

    Dialog dialog;

    int touchBack = 0;

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
                Intent intent = new Intent(MainActivity.this,ThanhToan.class);
                startActivity(intent);
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
        lnDatTruoc = findViewById(R.id.lnDatTruoc);

        imgVFPPre = findViewById(R.id.imgVFPPre);
        imgVFPNext = findViewById(R.id.imgVFPNext);
        btnClickPhong = findViewById(R.id.btnClickPhong);
        btnClickThucDon = findViewById(R.id.btnClickThucDon);
        btnClickThucDon.setBackgroundColor(Color.parseColor("#ffffff"));
        btnClickThucDon.setTextColor(getResources().getColor(R.color.colorPrimary));

        SharedPreferences preferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
        maUser = preferences.getString("MAUSER","");

        int diemTichLuy = 0;
        arrPhongTangTret = new ArrayList<>();
        gvListPhong = findViewById(R.id.gvTangTret);
        adapterPhong = new AdapterPhong(MainActivity.this,R.layout.gridview_content,arrPhongTangTret);

        arrThucDon = new ArrayList<>();
        gvThucDon = findViewById(R.id.gvThucDon);
        gvThucDon.setVisibility(View.GONE);
        adapterThucDon = new AdapterThucDon(MainActivity.this,R.layout.gridview_thucdon,arrThucDon);

        arrBanAn = new ArrayList<>();
        gvBanAn = findViewById(R.id.gvBanAn);
        adapterBanAn = new AdapterBanAn(MainActivity.this,R.layout.gridview_banan,arrBanAn);
        ShowAllDataFromDB();


        String sub = new String();
        if (maUser!=null)
        {
            sub = maUser.substring(0,2);
        }
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
            phong.setMaPhong(cursor.getString(0));
            phong.setLoaiPhong(cursor.getString(1));
            phong.setGiaPhong(cursor.getString(2));
            phong.setTrangThai(cursor.getString(3));
            arrPhongTangTret.add(phong);
        }
        adapterPhong.notifyDataSetChanged();
        gvListPhong.setAdapter(adapterPhong);

        Cursor cursor2 = database.rawQuery("SELECT * FROM BanAn",null);
        while (cursor2.moveToNext())
        {
            BanAn banAn = new BanAn();
            banAn.setMaBan(cursor2.getString(1));
            banAn.setLoaiBan(cursor2.getString(2));
            banAn.setTrangThai(cursor2.getString(3));
            arrBanAn.add(banAn);
        }
        adapterBanAn.notifyDataSetChanged();
        gvBanAn.setAdapter(adapterBanAn);

        int i = 0;
        int []imgThucPham = new int[38];
        imgThucPham[0] = R.drawable.thucdon_bonuong;
        imgThucPham[1] = R.drawable.thucdon_bolalop;
        imgThucPham[2] = R.drawable.thucdon_boxaocuhanh;
        imgThucPham[3] = R.drawable.thucdon_bonhungdam;
        imgThucPham[4] = R.drawable.thucdon_bonaume;
        imgThucPham[5] = R.drawable.thucdon_phobo;
        imgThucPham[6] = R.drawable.thucdon_garankfc;
        imgThucPham[7] = R.drawable.thucdon_gaxaophomai;
        imgThucPham[8] = R.drawable.thucdon_xoiga;
        imgThucPham[9] = R.drawable.thucdon_gaquay;
        imgThucPham[10] = R.drawable.thucdon_gahaplachanh;
        imgThucPham[11] = R.drawable.thucdon_laugachuacay;
        imgThucPham[12] = R.drawable.thucdon_comga;
        imgThucPham[13] = R.drawable.thucdon_bachtuocnuongsate;
        imgThucPham[14] = R.drawable.thucdon_mucchiennuocmam;
        imgThucPham[15] = R.drawable.thucdon_cuahapbia;
        imgThucPham[16] = R.drawable.thucdon_laucua;
        imgThucPham[17] = R.drawable.thucdon_saladhaisan;
        imgThucPham[18] = R.drawable.thucdon_mixaohaisan;
        imgThucPham[19] = R.drawable.thucdon_goitom;
        imgThucPham[20] = R.drawable.thucdon_lauhaisan;
        Cursor cursor1 = database.rawQuery("SELECT * FROM ThucPham",null);
        while (cursor1.moveToNext())
        {
            ThucPham thucPham = new ThucPham();
            thucPham.setMaThucPham(cursor1.getString(0));
            thucPham.setTenThucPham(cursor1.getString(1));
            thucPham.setGiaThucPham(cursor1.getString(2));
            thucPham.setLoaiThucPham(cursor1.getString(3));
            thucPham.setImgThucPham(imgThucPham[i]);
            arrThucDon.add(thucPham);
            i++;
        }

        adapterThucDon.notifyDataSetChanged();
        gvThucDon.setAdapter(adapterThucDon);
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


                txtDialogLoaiPhong.setText("Phòng "+arrPhongTangTret.get(i).getLoaiPhong());
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

        gvBanAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DatBan.class);
                BanAn banAn = arrBanAn.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BANAN",banAn);
                intent.putExtra("BUNDLEBANAN",bundle);
                startActivity(intent);
            }
        });

        gvThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Dialog_GoiMonAn.class);
                ThucPham thucPham = arrThucDon.get(position);
                intent.putExtra("MATHUCPHAM",thucPham.getMaThucPham());
                intent.putExtra("TENTHUCPHAM",thucPham.getTenThucPham());
                intent.putExtra("GIATHUCPHAM",thucPham.getGiaThucPham());
                intent.putExtra("MAKHACHHANG",maUser);
                intent.putExtra("IMGTHUCPHAM",thucPham.getImgThucPham());
                startActivity(intent);
            }
        });

        btnClickThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnDatTruoc.setVisibility(View.GONE);
                gvThucDon.setVisibility(View.VISIBLE);
                btnClickPhong.setBackgroundColor(Color.parseColor("#ffffff"));
                btnClickPhong.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnClickThucDon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnClickThucDon.setTextColor(Color.parseColor("#ffffff"));
            }
        });
        btnClickPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gvThucDon.setVisibility(View.GONE);
                lnDatTruoc.setVisibility(View.VISIBLE);
                btnClickThucDon.setBackgroundColor(Color.parseColor("#ffffff"));
                btnClickThucDon.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnClickPhong.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnClickPhong.setTextColor(Color.parseColor("#ffffff"));
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
        if (id == R.id.mnu_PhongDaDat) {
            Cursor cursor = database.rawQuery("SELECT * FROM PhongDat WHERE MaKhachHang='"+maUser+"'",null);
            if (cursor.getCount()>0)
            {
                Intent intent = new Intent(MainActivity.this,PhongDaDat.class);
                startActivity(intent);
            }
            else Toast.makeText(this,"Bạn chưa đặt phòng!",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.mnu_BanDaDat){
            Cursor cursorBanDaDat = database.rawQuery("SELECT * FROM DatBan WHERE MaKhachHang='"+maUser+"'",null);
            if (cursorBanDaDat.getCount()==0)
            {
                Toast.makeText(MainActivity.this,"Bạn chưa đặt bàn!",Toast.LENGTH_SHORT).show();
            }

            else {
                final Dialog dialogBanDaDat = new Dialog(MainActivity.this);
                dialogBanDaDat.setCancelable(false);
                dialogBanDaDat.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogBanDaDat.setContentView(R.layout.dialog_menubandadat);

                TextView txtBanDaDat_TenKhachHang = dialogBanDaDat.findViewById(R.id.txtBanDaDat_TenKhachHang);
                TextView txtBanDaDat_MaBan = dialogBanDaDat.findViewById(R.id.txtBanDaDat_MaBan);
                TextView txtBanDaDat_LoaiBan = dialogBanDaDat.findViewById(R.id.txtBanDaDat_Loai);
                TextView txtBanDaDat_ThoiGian = dialogBanDaDat.findViewById(R.id.txtBanDaDat_ThoiGian);
                TextView txtBanDaDat_GhiChu = dialogBanDaDat.findViewById(R.id.txtBanDatDat_GhiChu);
                FancyButton btnTroLaiBanDaDat = dialogBanDaDat.findViewById(R.id.btnTroLaiBanDaDat);
                FancyButton btnHuyBanDaDat = dialogBanDaDat.findViewById(R.id.btnHuyBanDaDat);

                cursorBanDaDat.moveToLast();
                String maBan = cursorBanDaDat.getString(1);
                txtBanDaDat_MaBan.setText(maBan.substring(1,maBan.length()));
                txtBanDaDat_LoaiBan.setText(cursorBanDaDat.getString(2));
                txtBanDaDat_TenKhachHang.setText(cursorBanDaDat.getString(4));
                txtBanDaDat_ThoiGian.setText(cursorBanDaDat.getString(5)+" ngày "+cursorBanDaDat.getString(6));
                txtBanDaDat_GhiChu.setText(cursorBanDaDat.getString(7));
                btnTroLaiBanDaDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBanDaDat.cancel();
                    }
                });
                btnHuyBanDaDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.execSQL("DELETE FROM DatBan WHERE MaKhachHang='"+maUser+"'");
                        Toast.makeText(MainActivity.this,"Đã hủy đặt bàn thành công",Toast.LENGTH_SHORT).show();
                        dialogBanDaDat.cancel();
                    }
                });
                dialogBanDaDat.show();
            }
        } if (id == R.id.mnu_MaGiamGia){
            Cursor cursor = database.rawQuery("SELECT * FROM MaGiamGia WHERE MaKhachHang='"+maUser+"'",null);
             if (cursor.getCount()==0)
             {
                 Toast.makeText(MainActivity.this,"Bạn chưa đổi mã giảm giá!",Toast.LENGTH_SHORT).show();
             }
            else
             {
                 cursor.moveToLast();
                 String maGiamGia = cursor.getString(2);
                 Toast.makeText(MainActivity.this,"Mã giảm giá của bạn là: "+maGiamGia,Toast.LENGTH_LONG).show();
             }
        } if (id == R.id.mnu_XemPhong){
            Intent intent = new Intent(MainActivity.this,XemKhachSan.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_KhuyenMai) {
            this.startActivity(new Intent(this,ManHinhKhuyenMai.class));
        } else if (id == R.id.nav_DoiDiem) {
            xuLyDoiDiem();
        } else if (id == R.id.nav_DoiMatKhau) {
            xuLyDoiMatKhau();
        } else if (id == R.id.nav_DanhGia) {
            xuLyDanhGia();
        } else if (id == R.id.nav_DangXuat) {
            xuLyDangXuat();
        } else if (id == R.id.nav_GioiThieu) {
            xuLyGioiThieu();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void xuLyGioiThieu() {
        this.startActivity(new Intent(this,GioiThieu.class));
    }

    private void xuLyDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    private void xuLyDanhGia() {
        this.startActivity(new Intent(this,ManHinhDanhGia.class));
    }

    private void xuLyDoiMatKhau() {
        this.startActivity(new Intent(this,DoiMatKhau.class));
    }

    private void xuLyDoiDiem() {
        this.startActivity(new Intent(this,DoiDiem.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (touchBack==1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(keyCode == KeyEvent.KEYCODE_BACK)
         {
             touchBack++;
             Toast.makeText(MainActivity.this,"Nhấn lần nữa để thoát ứng dụng",Toast.LENGTH_SHORT).show();
         }
        return true;
    }
}
