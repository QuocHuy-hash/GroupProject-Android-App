package com.example.duan1.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duan1.Adapter.slidersAdapter;
import com.example.duan1.R;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        viewPager = view.findViewById(R.id.viewPager_slide);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        slidersAdapter = new slidersAdapter(getContext() , getListPhoto());
        viewPager.setAdapter(slidersAdapter);

        circleIndicator.setViewPager(viewPager);
        slidersAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSliderImg();
        return view;
    }

    private void autoSliderImg() {

        if(mTimer == null) {
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
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500 , 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mTimer != null) {
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

        return  mListPhoto;
    }
}