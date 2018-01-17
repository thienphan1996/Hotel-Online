package com.example.dell.datphongkhachsanonline;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.adapter.AdapterListviewMonAnDaDat;
import com.example.dell.model.GoiMon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThanhToan extends AppCompatActivity {

    SQLiteDatabase database = null;

    ArrayList<GoiMon> arrThanhToanMonAn;
    ListView lvThanhToanMonAn;
    AdapterListviewMonAnDaDat adapterMonAnDat;

    TextView txtTongHoaDon,txtDiemTichLuy;
    LinearLayout lnTongHoaDon;

    String maUser = new String();
    int tongHoaDon = 0;
    SimpleDateFormat spf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar calendar = Calendar.getInstance();

        spf = new SimpleDateFormat("dd/MM/yyyy");

        FloatingActionButton floatThanhToan = findViewById(R.id.floatThanhToan);
        final SimpleDateFormat finalSpf = spf;
        floatThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThanhToan.this);
                builder.setMessage("Bạn có muốn thanh toán không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maKhachHang = arrThanhToanMonAn.get(0).getMaKhachHang();
                        String maBan = arrThanhToanMonAn.get(0).getMaBan();
                        String thoiGianThanhToan = spf.format(calendar.getTime());
                        database.execSQL("INSERT INTO HoaDon VALUES(null,'"+maKhachHang+"',null,'"+maBan+"','"+tongHoaDon+"','"+thoiGianThanhToan+"')");
                        Toast.makeText(ThanhToan.this,"Đã gửi yêu cầu,quý khách vui lòng đợi nhân viên thanh toán hoặc đến quầy tiếp tân thanh toán trực tiếp",Toast.LENGTH_LONG).show();
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

        addControls();
        addEvents();
    }

    private void addControls() {
        database = openOrCreateDatabase("dbKhachSan.sqlite",MODE_PRIVATE,null);

        txtTongHoaDon = findViewById(R.id.txtTongHoaDon);
        txtDiemTichLuy = findViewById(R.id.txtDiemTichLuy);
        lnTongHoaDon = findViewById(R.id.lnTongHoaDon);


        arrThanhToanMonAn = new ArrayList<>();
        lvThanhToanMonAn = findViewById(R.id.lvThanhToanMonAn);
        adapterMonAnDat = new AdapterListviewMonAnDaDat(ThanhToan.this,R.layout.listview_thanhtoanmonan,arrThanhToanMonAn);
//        SharedPreferences sharedPreferences = getSharedPreferences("GHINHOMAUSER",MODE_PRIVATE);
//        maUser = sharedPreferences.getString("MAUSER","");

        showAllDataOnListView();


        if (arrThanhToanMonAn.size()==0)
        {
            lnTongHoaDon.setVisibility(View.GONE);
        }
        for (int i = 0; i < arrThanhToanMonAn.size();i++)
        {
            tongHoaDon += Integer.parseInt(arrThanhToanMonAn.get(i).getGiaThucPham())*Integer.parseInt(arrThanhToanMonAn.get(i).getSoLuong());
        }

        String strTongHoaDon = tongHoaDon+" VNĐ";
        txtTongHoaDon.setText(strTongHoaDon);
        int diemTichLuy = tongHoaDon/10000;
        txtDiemTichLuy.setText(diemTichLuy+" điểm tích lũy");
         if (arrThanhToanMonAn.size()>5)
         {
             LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lvThanhToanMonAn.getLayoutParams();
             layoutParams.height = 600;
             lvThanhToanMonAn.setLayoutParams(layoutParams);
         }
    }

    private void showAllDataOnListView() {
        Cursor cursor = database.rawQuery("SELECT * FROM GoiMon",null);
        while (cursor.moveToNext())
        {
            GoiMon goiMon = new GoiMon();
            goiMon.setMaThucPham(cursor.getString(1));
            goiMon.setTenThucPham(cursor.getString(2));
            goiMon.setGiaThucPham(cursor.getString(3));
            goiMon.setMaKhachHang(cursor.getString(4));
            goiMon.setMaBan(cursor.getString(5));
            goiMon.setSoLuong(cursor.getInt(6)+"");
            arrThanhToanMonAn.add(goiMon);
        }
        adapterMonAnDat.notifyDataSetChanged();
        lvThanhToanMonAn.setAdapter(adapterMonAnDat);
    }

    private void addEvents() {
        lvThanhToanMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThanhToan.this);
                builder.setMessage("Quý khách có muốn xóa món ăn không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.execSQL("DELETE FROM GoiMon WHERE MaThucPham='"+arrThanhToanMonAn.get(position).getMaThucPham()+"'");
                        String tenMonAn = arrThanhToanMonAn.get(position).getTenThucPham();
                        tongHoaDon -= Integer.parseInt(arrThanhToanMonAn.get(position).getGiaThucPham())*Integer.parseInt(arrThanhToanMonAn.get(position).getSoLuong());
                        arrThanhToanMonAn.remove(position);
                        String strTongHoaDon = tongHoaDon+" VNĐ";
                        txtTongHoaDon.setText(strTongHoaDon);
                        int diemTichLuy = tongHoaDon/10000;
                        txtDiemTichLuy.setText(diemTichLuy+" điểm tích lũy");
                        adapterMonAnDat.notifyDataSetChanged();
                        if (arrThanhToanMonAn.size()<5)
                        {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lvThanhToanMonAn.getLayoutParams();
                            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            lvThanhToanMonAn.setLayoutParams(layoutParams);
                        }
                        Toast.makeText(ThanhToan.this,"Đã xóa món ăn: "+tenMonAn,Toast.LENGTH_SHORT).show();
                        if (arrThanhToanMonAn.size()==0)
                        {
                            lnTongHoaDon.setVisibility(View.GONE);
                        }
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
    }
}
