package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.duan1.Adapter.dmucBatDongSanAdapter;
import com.example.duan1.model.danhMucBatDongSan;

import java.util.ArrayList;
import java.util.List;

public class chonDanhMucBDSActivity extends AppCompatActivity {
    ImageView backPage;
    RecyclerView rcvChonDanhMuc;
    List<danhMucBatDongSan> listDm;
    com.example.duan1.Adapter.dmucBatDongSanAdapter dmucBatDongSanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_danh_muc_bdsactivity);
        initUi();
        clickBackPage();
        listDm = new ArrayList<>();
        dmucBatDongSanAdapter = new dmucBatDongSanAdapter(getApplicationContext() , createList());

        rcvChonDanhMuc.setAdapter(dmucBatDongSanAdapter);
        rcvChonDanhMuc.setLayoutManager(new LinearLayoutManager(this));


    }

    private List<danhMucBatDongSan> createList() {
        listDm.add(new danhMucBatDongSan("Căn hộ/Chung cư"));
        listDm.add(new danhMucBatDongSan("Nhà ở"));
        listDm.add(new danhMucBatDongSan("Đất "));
        listDm.add(new danhMucBatDongSan("Văn Phòng"));
        listDm.add(new danhMucBatDongSan("Phòng trọ"));

        return listDm;
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
        rcvChonDanhMuc = findViewById(R.id.rcvChonDanhMuc);
    }

}