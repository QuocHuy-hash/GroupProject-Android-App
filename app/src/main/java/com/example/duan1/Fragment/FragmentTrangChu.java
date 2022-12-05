package com.example.duan1.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.NewsTrangChuAdapter;
import com.example.duan1.Adapter.historyNewsAdapter;
import com.example.duan1.Adapter.slidersAdapter;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.NewsTrangChu;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.historyNews;
import com.example.duan1.model.photosSlider;
import com.example.duan1.model.thoiTrangNews;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import me.relex.circleindicator.CircleIndicator;


public class FragmentTrangChu extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private slidersAdapter slidersAdapter;
    private List<photosSlider> mListPhoto = new ArrayList<>();
    private Timer mTimer;
    public MainActivity mainActivity;
    private RecyclerView rcvNewsTrangChu;
    private List<NewsTrangChu> newsTrangChuList = new ArrayList<>();
    private NewsTrangChuAdapter mNewsTrangChuAdapter;
    private List<String> listChild = new ArrayList<>();
    private List<String> listChild1 = new ArrayList<>();
    private List<String> listChild2 = new ArrayList<>();
    DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
    DatabaseReference myData1 = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
    DatabaseReference myData2 = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        mainActivity = (MainActivity) getActivity();


        viewPager = view.findViewById(R.id.viewPager_slide);

        circleIndicator = view.findViewById(R.id.circle_indicator);

        slidersAdapter = new slidersAdapter(getContext(), getListPhoto());
        viewPager.setAdapter(slidersAdapter);

        circleIndicator.setViewPager(viewPager);
        slidersAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSliderImg();
        listChild1();
        listChild2();
        listChild3();
        getListNews();
        rcvNewsTrangChu = view.findViewById(R.id.rcv_news_trangchu);
        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
        mNewsTrangChuAdapter.setDATA(newsTrangChuList);
        rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
//        rcvNewsTrangChu.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
//            }
//        });
        return view;
    }

    private void autoSliderImg() {

        if (mTimer == null) {
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentitem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;

                        if (currentitem < totalItem) {
                            currentitem++;
                            viewPager.setCurrentItem(currentitem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

    }


    private List<photosSlider> getListPhoto() {
//    List<photosSlider> list = new ArrayList<>();

        mListPhoto.add(new photosSlider(R.drawable.img_slider1));
        mListPhoto.add(new photosSlider(R.drawable.img_slider2));
        mListPhoto.add(new photosSlider(R.drawable.img_slider3));
        mListPhoto.add(new photosSlider(R.drawable.img_slider4));

        return mListPhoto;
    }
    private void getListNews() {
        for (int i = 0; i < listChild.size(); i++) {
            myData.child(listChild.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    giaiTriNews giaiTriNews = snapshot.getValue(giaiTriNews.class);
                    newsTrangChuList.add(new NewsTrangChu(giaiTriNews.getTitle() , giaiTriNews.getDescription(),
                            giaiTriNews.getPrice(),giaiTriNews.getDate(),true, ""));


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
        for (int i = 0; i < listChild1.size(); i++) {
            myData2.child(listChild1.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    thoiTrangNews thoiTrangNews = snapshot.getValue(com.example.duan1.model.thoiTrangNews.class);
                    newsTrangChuList.add(new NewsTrangChu(thoiTrangNews.getTitlePost(), thoiTrangNews.getDescriptionPost(),
                            thoiTrangNews.getPrice(), thoiTrangNews.getDate(), true , ""));

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
        for (int i = 0; i < listChild2.size(); i++) {
            myData1.child(listChild2.get(i)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    BDSNews bdsNews = snapshot.getValue(BDSNews.class);

                    newsTrangChuList.add(new NewsTrangChu(bdsNews.getTitle() , bdsNews.getDescription() ,
                            bdsNews.getPrice(),bdsNews.getDate(),false , ""));
                    mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
                    mNewsTrangChuAdapter.setDATA(newsTrangChuList);
                    rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
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



    }
    private List<String> listChild1() {
        listChild.add(new String("Nhạc cụ"));
        listChild.add(new String("Sách"));
        listChild.add(new String("Đồ thể thao, Dã ngoại"));
        listChild.add(new String("Đồ sưu tầm  ,Đồ cổ"));
        listChild.add(new String("Thiết bị chơi game"));
        listChild.add(new String("Sở thích khác"));

        return listChild;
    }

    private List<String> listChild2() {
        listChild1.add(new String("Quần áo"));
        listChild1.add(new String("Đồng hồ"));
        listChild1.add(new String("Giày dép"));
        listChild1.add(new String("Túi xách"));
        listChild1.add(new String("Nước hoa"));
        listChild1.add(new String("Phụ kiện khác"));

        return listChild1;
    }

    private List<String> listChild3() {
        listChild2.add(new String("Chung cư"));
        listChild2.add(new String("Nhà ở"));
        listChild2.add(new String("Đất"));
        listChild2.add(new String("Văn Phòng"));
        listChild2.add(new String("Phòng trọ"));

        return listChild2;
    }
}