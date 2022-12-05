package com.example.duan1.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.NewsTrangChuAdapter;
import com.example.duan1.Adapter.slidersAdapter;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.model.NewsTrangChu;
import com.example.duan1.model.photosSlider;


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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        mainActivity = (MainActivity) getActivity();
//âsasasasassa
        viewPager = view.findViewById(R.id.viewPager_slide);

        circleIndicator = view.findViewById(R.id.circle_indicator);

        slidersAdapter = new slidersAdapter(getContext(), getListPhoto());
        viewPager.setAdapter(slidersAdapter);

        circleIndicator.setViewPager(viewPager);
        slidersAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSliderImg();

        rcvNewsTrangChu = view.findViewById(R.id.rcv_news_trangchu);
        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
        mNewsTrangChuAdapter.setDATA(getListNews());
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
    private List<NewsTrangChu> getListNews() {
//        List<String> UrlArr = new ArrayList<>();
//        UrlArr.add("https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149");
//        UrlArr.add("https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149");
//        UrlArr.add("https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149");


        newsTrangChuList.add(new NewsTrangChu(
                "String title1"
                , "String descripsion"
                , "String fee"
                , "String time"
                , false
                ,"https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149"));
        newsTrangChuList.add(new NewsTrangChu(
                "String title2"
                , "String descripsion"
                , "String fee"
                , "String time"
                , false
                ,"https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149"));
        newsTrangChuList.add(new NewsTrangChu(
                "String title3"
                , "String descripsion"
                , "String fee"
                , "String time"
                , false
                ,"https://firebasestorage.googleapis.com/v0/b/cho-tot-du-an-1.appspot.com/o/Image.jpg%2FImage%20image%3A62%2FN%C6%B0%E1%BB%9Bc%20Hoa%20Chionhs%20H%C3%A3ng?alt=media&token=d6d9bfaf-f8bf-4225-b388-031e6456b149"));
        return newsTrangChuList;
    }
}