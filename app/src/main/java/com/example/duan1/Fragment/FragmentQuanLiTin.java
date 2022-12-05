package com.example.duan1.Fragment;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.historyNewsAdapter;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.historyNews;
import com.example.duan1.model.thoiTrangNews;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FragmentQuanLiTin extends Fragment {
    private RecyclerView rcvQuanLyTinDang;
    private historyNewsAdapter historyNewsAdapter;
    private ArrayList<giaiTriNews> listHistoryGiaTriNews ;
    private ArrayList<thoiTrangNews> listHistoryThoiTrangNews;
    private String nameUser;
    private int idUser ;
    private List<String> listChild = new ArrayList<>();
    private List<String> listChild1 = new ArrayList<>();
    private List<String> listChild2 = new ArrayList<>();
    private List<historyNews> listHistoryNews = new ArrayList<>();
    DatabaseReference myData;
    DatabaseReference myData1;
    DatabaseReference myData2;
    StorageReference imageFolder;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_li_tin, container, false);
        rcvQuanLyTinDang = view.findViewById(R.id.rcvQuanLyTin);
        listHistoryGiaTriNews  = new ArrayList<>();
        listHistoryThoiTrangNews = new ArrayList<>();

        imageFolder = FirebaseStorage.getInstance().getReference().child("Image.jpg");

        myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
        myData1 = FirebaseDatabase.getInstance().getReference("Tin").child("BSD");
        myData2 = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");



        idUser = mainActivity.id;
        nameUser = mainActivity.name;
        listChild1();
        listChild2();
        listChild3();

        historyNewsAdapter = new historyNewsAdapter(getContext() , getListHistoryNews());
        rcvQuanLyTinDang.setLayoutManager( new LinearLayoutManager(getContext()));
        rcvQuanLyTinDang.setAdapter(historyNewsAdapter);
        return view;
    }

    private List<historyNews> getListHistoryNews() {
        for (int i = 0; i < listChild.size() ;i++) {
            myData.child(listChild.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    giaiTriNews giaiTriNews = snapshot.getValue(giaiTriNews.class);
                    listHistoryGiaTriNews.add(giaiTriNews);

                    listHistoryNews.add(new historyNews(1, giaiTriNews.getTitle() , giaiTriNews.getDescription() , "12-12-2022"));

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        for (int i = 0; i< listChild1.size(); i++) {
            myData2.child(listChild1.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    thoiTrangNews thoiTrangNews = snapshot.getValue(com.example.duan1.model.thoiTrangNews.class);
                    listHistoryNews.add(new historyNews(1, thoiTrangNews.getTitlePost() , thoiTrangNews.getDescriptionPost() , "12-12-2022"));

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        for(int i = 0; i < listChild2.size(); i++) {
            myData1.child(listChild2.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    BDSNews bdsNews = snapshot.getValue(BDSNews.class);
                    listHistoryNews.add(new historyNews(1 , bdsNews.getTitle() ,bdsNews.getDescription(),"12-11-2002"));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return listHistoryNews;
    }

    private List<String>  listChild1 (){
        listChild.add(new String("Nhạc cụ"));
        listChild.add(new String("Sách"));
        listChild.add(new String("Đồ thể thao, Dã ngoại"));
        listChild.add(new String("Đồ sưu tầm  ,Đồ cổ"));
        listChild.add(new String("Thiết bị chơi game"));
        listChild.add(new String("Sở thích khác"));

        return listChild;
    }

    private List<String>  listChild2 (){
        listChild1.add(new String("Quần áo"));
        listChild1.add(new String("Đồng hồ"));
        listChild1.add(new String("Giày dép"));
        listChild1.add(new String("Túi xách"));
        listChild1.add(new String("Nước hoa"));
        listChild1.add(new String("Phụ kiện khác"));

        return listChild1;
    }
    private List<String>  listChild3 () {
        listChild2.add(new String("Chung cư"));
        listChild2.add(new String("Nhà ở"));
        listChild2.add(new String("Đất"));
        listChild2.add(new String("Văn Phòng"));
        listChild2.add(new String("Phòng trọ"));

        return listChild2;
    }

}