package com.example.duan1.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1.Adapter.historyNewsAdapter;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.NewsTrangChu;
import com.example.duan1.model.Users;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.historyNews;
import com.example.duan1.model.thoiTrangNews;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private List<historyNews> listHistoryNews;
    private List<historyNews> listHistoryNewsBDS;
    private List<historyNews> listHistoryNewsGT;
    private List<historyNews> listHistoryNewsTT;

    private Context mContext;

    public static String nameUser;
    public static int idUser;
    private String name;
    private List<String> listChild = new ArrayList<>();
    private List<String> listChild1 = new ArrayList<>();
    private List<String> listChild2 = new ArrayList<>();
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    int id = 0;

    DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
    DatabaseReference myData1 = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
    DatabaseReference myData2 = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_li_tin, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            rcvQuanLyTinDang = view.findViewById(R.id.rcvQuanLyTin);
            mainActivity = (MainActivity) getActivity();
            listHistoryNews = new ArrayList<>();
            listHistoryNewsBDS = new ArrayList<>();
            listHistoryNewsTT = new ArrayList<>();
            listHistoryNewsGT = new ArrayList<>();

//            getUser();
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Users");
            if (currentUser != null) {
                getUser();
            }else {
                Toast.makeText(mContext, "current User Null", Toast.LENGTH_SHORT).show();
            }


            getListHistoryNews();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    historyNewsAdapter = new historyNewsAdapter(getContext(),mainActivity, listHistoryNews);
                    rcvQuanLyTinDang.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcvQuanLyTinDang.setAdapter(historyNewsAdapter);

                }
            }, 500);
        }

        return view;
    }
    private void getUser() {
        List<Users> mListUser = new ArrayList<>();

        String email = currentUser.getEmail();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListUser.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Users user = snapshot1.getValue(Users.class);
                    mListUser.add(user);
                }

                for (int i = 0; i < mListUser.size(); i++) {
                    if (mListUser.get(i).getEmail().equals(email)) {
                        nameUser = (mListUser.get(i).getName());
                        idUser = mListUser.get(i).getId();
                        name = nameUser;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error get Id and Name from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getListHistoryNews() {
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listHistoryNewsGT.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    giaiTriNews giaiTriNews = snapshot1.getValue(giaiTriNews.class);

                    if (giaiTriNews.getIdUser() == idUser) {
                        listHistoryNews.add(new historyNews(id, giaiTriNews.getTitle(),
                                giaiTriNews.getDescription(), giaiTriNews.getDate(),
                                giaiTriNews.getTenDanhMuc(),giaiTriNews.getImage()));
                        id++;
                    }else if(name.equals("Admin")){
                        listHistoryNews.add(new historyNews(id, giaiTriNews.getTitle(), giaiTriNews.getDescription() , giaiTriNews.getDate(), giaiTriNews.getTenDanhMuc()));
                        id++;

                    }
                }
//                addData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myData2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listHistoryNewsTT.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    thoiTrangNews thoiTrangNews = snapshot1.getValue(com.example.duan1.model.thoiTrangNews.class);
                    if (thoiTrangNews.getIdUser() == idUser) {
                        listHistoryNews.add(new historyNews(id, thoiTrangNews.getTitlePost(),
                                thoiTrangNews.getDescriptionPost(), thoiTrangNews.getDate(),
                                thoiTrangNews.getTenDanhMuc(),thoiTrangNews.getImage()));
                        id++;
                    }else if(name.equals("Admin")){
                        listHistoryNews.add(new historyNews(id, thoiTrangNews.getTitlePost(), thoiTrangNews.getDescriptionPost() , thoiTrangNews.getDate(), thoiTrangNews.getTenDanhMuc()));
                        id++;

                    }

                }
//                addData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            myData1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listHistoryNewsBDS.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        BDSNews bdsNews = snapshot1.getValue(BDSNews.class);
                        if (bdsNews.getIdUser() == idUser) {
                            listHistoryNews.add(new historyNews(id, bdsNews.getTitle(),
                                    bdsNews.getDescription() , bdsNews.getDate(),
                                    bdsNews.getTenDanhMuc(), bdsNews.getImage()));
                            id++;
                        }else if(name.equals("Admin")){
                            listHistoryNews.add(new historyNews(id, bdsNews.getTitle(), bdsNews.getDescription() , bdsNews.getDate(), bdsNews.getTenDanhMuc()));
                            id++;

                        }
                    }
//                    addData();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }



}