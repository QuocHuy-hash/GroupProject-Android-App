package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.duan1.Fragment.FragmentDaoCho;
import com.example.duan1.Fragment.FragmentQuanLiTin;
import com.example.duan1.Fragment.FragmentThem;
import com.example.duan1.Fragment.FragmentTrangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    FragmentTrangChu fragmentTrangChu = new FragmentTrangChu();
    FragmentQuanLiTin fragmentQuanLiTin = new FragmentQuanLiTin();
    FragmentDaoCho fragmentDaoCho = new FragmentDaoCho();
    FragmentThem fragmentThem = new FragmentThem();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogChooseListProduct();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTrangChu).commit();
                        return true;

                    case R.id.believe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentQuanLiTin).commit();
                        return true;

                    case R.id.fab:
                        return true;

                    case R.id.market:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaoCho).commit();
                        return true;

                    case R.id.more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentThem).commit();
                        return true;
                }

                return false;
            }
        });
        
    }

    private void showDialogChooseListProduct() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_chon_danh_muc);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        RelativeLayout layout_batDongSan = dialog.findViewById(R.id.layout_batDongSan);
        RelativeLayout layout_XeCo = dialog.findViewById(R.id.layout_XeCo);
        RelativeLayout layout_thoiTrang = dialog.findViewById(R.id.layout_thoiTrang);
        RelativeLayout layout_dienTu = dialog.findViewById(R.id.layout_dienTu);
        RelativeLayout layout_giaiTri = dialog.findViewById(R.id.layout_giaiTri);
        RelativeLayout layout_dienGiaDung = dialog.findViewById(R.id.layout_dienGiaDung);
        ImageView icon_close = dialog.findViewById(R.id.icon_close);

        icon_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layout_giaiTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , chonDanhMucGiaiTriActivity.class));
            }
        });
        layout_dienGiaDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , chonDanhMucDienMayActivity.class));

            }
        });
        layout_batDongSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "danh mục batDongSan", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext() , chonDanhMucBDSActivity.class));
            }
        });
        layout_XeCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , chonDanhMucXeCoActivity.class));

            }
        });
        layout_thoiTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , chonDanhMucThoiTrangAcrivity.class));

            }
        });
        layout_dienTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , chonDanhMucDoDienTuActivity.class));

            }
        });
        dialog.show();
    }


    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTrangChu).commit();
    }
}
