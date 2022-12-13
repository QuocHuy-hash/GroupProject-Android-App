package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1.Adapter.NewsTrangChuAdapter;
import com.example.duan1.Adapter.chitietDacDiemAdapter;
import com.example.duan1.model.NewsTrangChu;
import com.example.duan1.model.chitietdacdiem;

import java.util.List;



public class chitiet_news extends AppCompatActivity {
    private NewsTrangChuAdapter newsTrangChuAdapter;
    private chitietDacDiemAdapter chitietDacDiemAdapter;
    private RecyclerView rcvTinkhac1,rcvTinkhac2,rcvDacdiem;
    private ImageView imgavatar,imgfavoriteheart,imgPic;
    private TextView tvTitle,tvFee,tvaddress,tvaddressMap,tvTime,
            tvTitleDacDiem,tvTitleMota,tvMota,tvMotaLienhe,
            tvNameNguoiBan,tvXemTrang,tvTitleTinkhac1,tvTitleTinkhac2;
    private LinearLayout layoutDacDiem;
    private int id;
    private String Loai;
    private List<NewsTrangChu> newsTrangChuList1,newsTrangChuList2;
//    1. cùng người bán
//    2. cùng loại tin
    private List<chitietdacdiem> chitietdacdiemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_news);
        unitUI();
        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("id",-1) == -1 && bundle.getString("Loai") == null){
            Toast.makeText(this, "không thể get id hoặc phân loại", Toast.LENGTH_SHORT).show();
//            dùng intent và putextra đẩy id và loại sản phẩm để get được sản phẩm từ firebase
        }
        id = bundle.getInt("id");
        Loai = bundle.getString("Loai");

        setData();


        setDataDacDiemSanPham();
        rcvDacdiem = findViewById(R.id.rcv_chitiet_dacdiem);
        chitietDacDiemAdapter = new chitietDacDiemAdapter(chitiet_news.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvDacdiem.setLayoutManager(gridLayoutManager);
        chitietDacDiemAdapter.setData(chitietdacdiemList);
        rcvDacdiem.setAdapter(chitietDacDiemAdapter);

        
        setDataTinCungSeller();
        rcvTinkhac1 = findViewById(R.id.rcv_chitiet_tinkhac1);
        newsTrangChuAdapter = new NewsTrangChuAdapter(chitiet_news.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcvTinkhac1.setLayoutManager(linearLayoutManager);
        newsTrangChuAdapter.setDATA(newsTrangChuList1);
        rcvTinkhac1.setAdapter(newsTrangChuAdapter);

        setDataTinCungLoai();
        rcvTinkhac2 = findViewById(R.id.rcv_chitiet_tinkhac1);
        newsTrangChuAdapter = new NewsTrangChuAdapter(chitiet_news.this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcvTinkhac2.setLayoutManager(linearLayoutManager2);
        newsTrangChuAdapter.setDATA(newsTrangChuList2);
        rcvTinkhac2.setAdapter(newsTrangChuAdapter);


    }

    private void setDataDacDiemSanPham() {

    }

    // cùng data giống như News bên trang chủ
    private void setDataTinCungLoai() {

    }

    private void setDataTinCungSeller() {
    }

    private void setData() {
        tvTitle.setText("");
        tvFee.setText("");
        tvaddress.setText("");
        tvTime.setText("");

//        nếu loại sản phẩm có đặc điểm để hiển thị ở đây anh set dữ liệu giúp em xem thử ở layout nó là RCV chitiet dacdiem
        if(Loai == ""){
            tvTitleDacDiem.setText("");
        }else if(Loai == ""){
            tvTitleDacDiem.setText("");
        }else {
            layoutDacDiem.setVisibility(View.GONE);
//            nếu set hết không có là nó sẽ biến mất
        }

        tvMota.setText("");
        tvMotaLienhe.setText("");
        tvMotaLienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnsLienHe();
            }
        });

        tvNameNguoiBan.setText("");
        tvXemTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileSeller();
            }
        });

//      Ảnh chính
        Glide.with(this).load("").into(imgPic);
//      Avatar
        Glide.with(this).load("").into(imgavatar);
//      setfavorite
        if(false){
            imgfavoriteheart.setImageResource(R.drawable.ic_item_trangchu_favorite);
        }else{
            imgfavoriteheart.setImageResource(R.drawable.ic_item_trangchu_unfavorite);
        }
    }

    private void profileSeller() {
//        mở đến trang người bán.
    }

    private void SnsLienHe() {
//      code mở điện thoại liên hệ.
    }

    private void unitUI() {

        rcvDacdiem = findViewById(R.id.rcv_chitiet_dacdiem);
        rcvTinkhac1 = findViewById(R.id.rcv_chitiet_tinkhac1);
        rcvTinkhac2 = findViewById(R.id.rcv_chitiet_tinkhac1);

        imgavatar = findViewById(R.id.img_chitiet_nguoiban_avatar);
        imgfavoriteheart = findViewById(R.id.img_chitiet_favorite);
        imgPic = findViewById(R.id.img_chitiet_pic);

        tvTitle = findViewById(R.id.tv_chitiet_title);
        tvTitleDacDiem = findViewById(R.id.tv_chitiet_title_dacdiem);
        tvTitleMota = findViewById(R.id.tv_chitiet_title_mota);
        tvTitleTinkhac1 = findViewById(R.id.tv_chitiet_tinkhac1);
        tvTitleTinkhac2 = findViewById(R.id.tv_chitiet_tinkhac2);

        tvFee = findViewById(R.id.tv_chitiet_fee);
        tvaddress = findViewById(R.id.tv_chitiet_location);
        tvaddressMap = findViewById(R.id.tv_chitiet_location_map);
        tvTime = findViewById(R.id.tv_chitiet_time);

        tvMota = findViewById(R.id.tv_chitiet_mota_thongtin);
        tvMotaLienhe = findViewById(R.id.tv_chitiet_mota_thongtinlienhe);

        tvNameNguoiBan = findViewById(R.id.tv_chitiet_nguoiban_name);
        tvXemTrang = findViewById(R.id.tv_chitiet_nguoiban_xemtrang);

        layoutDacDiem = findViewById(R.id.layout_chitiet_dacdiem);
    }
}