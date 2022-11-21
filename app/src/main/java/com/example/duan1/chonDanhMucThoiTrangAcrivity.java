package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.duan1.Adapter.dmucBatDongSanAdapter;
import com.example.duan1.Adapter.dmucThoiTrangAdapter;
import com.example.duan1.model.danhMucBatDongSan;
import com.example.duan1.model.thoiTrang;

import java.util.ArrayList;
import java.util.List;

public class chonDanhMucThoiTrangAcrivity extends AppCompatActivity {
    ImageView backPage;
    RecyclerView rcvChonDanhMuc;
    List<thoiTrang> listTT;
    dmucThoiTrangAdapter dmucThoiTrangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_danh_muc_thoi_trang_acrivity);
        initUi();
        clickBackPage();

        listTT = new ArrayList<>();
        dmucThoiTrangAdapter = new dmucThoiTrangAdapter(getApplicationContext() , getList());

        rcvChonDanhMuc.setAdapter(dmucThoiTrangAdapter);
        rcvChonDanhMuc.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<thoiTrang> getList() {
        listTT.add(new thoiTrang("Quần áo"));
        listTT.add(new thoiTrang("Đồng hồ"));
        listTT.add(new thoiTrang("Giày dép"));
        listTT.add(new thoiTrang("Túi xách"));
        listTT.add(new thoiTrang("Nước hoa"));
        listTT.add(new thoiTrang("Phụ kiện khác"));
        return listTT;
    }

    private void clickBackPage() {
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initUi() {
        backPage = findViewById(R.id.icon_back);
        rcvChonDanhMuc = findViewById(R.id.rcvChonDanhMucThoiTrang);
    }
}