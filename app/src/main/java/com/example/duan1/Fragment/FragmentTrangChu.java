package com.example.duan1.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1.Adapter.NewsTrangChuAdapter;
import com.example.duan1.Adapter.rsSearchAdapter;
import com.example.duan1.Adapter.slidersAdapter;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.dangTinThoiTrangActivity;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.NewsTrangChu;
import com.example.duan1.model.direction;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.photosSlider;
import com.example.duan1.model.product_type;
import com.example.duan1.model.resultSearch;
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
    private rsSearchAdapter rsSearchAdapter;
    private List<photosSlider> mListPhoto = new ArrayList<>();
    private Timer mTimer;
    private String loaiTin;
    private SearchView searchView;
    private Spinner spn_phanLoaiTin;
    public MainActivity mainActivity;
    private RecyclerView rcvNewsTrangChu ,rcvFilter;
    private List<NewsTrangChu> newsTrangChuList = new ArrayList<>();
    private NewsTrangChuAdapter mNewsTrangChuAdapter;
    private List<String> listChild = new ArrayList<>();
    private List<String> listChild1 = new ArrayList<>();
    private List<String> listChild2 = new ArrayList<>();
    private List<resultSearch> listFilter;
    DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
    DatabaseReference myData1 = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
    DatabaseReference myData2 = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        mainActivity = (MainActivity) getActivity();
        rcvNewsTrangChu = view.findViewById(R.id.rcv_news_trangchu);
        spn_phanLoaiTin = view.findViewById(R.id.spn_phanLoaiTin);
        viewPager = view.findViewById(R.id.viewPager_slide);
        rcvFilter = view.findViewById(R.id.rcvFilter);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        slidersAdapter = new slidersAdapter(getContext(), getListPhoto());
        viewPager.setAdapter(slidersAdapter);
        searchView = view.findViewById(R.id.searchView);
        circleIndicator.setViewPager(viewPager);
        slidersAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        searchView.clearFocus();
        autoSliderImg();
        listChild1();
        listChild2();
        listChild3();
        clickSpinerLoaiTin();
//        newsTrangChuList.clear();
        getListNews();
        getValuesSpinner();
        searchNews();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//                rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
//                mNewsTrangChuAdapter.setDATA(newsTrangChuList);
//                rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
//            }
//        }, 500);
//        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
//        mNewsTrangChuAdapter.setDATA(newsTrangChuList);
//        rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
//        rcvNewsTrangChu.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
//            }
//        });
        return view;
    }

    private void searchNews() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()) {
                    rcvFilter.setVisibility(View.INVISIBLE);
                }else {
                    rcvFilter.setVisibility(View.VISIBLE);
                    filterList(newText);
                }

                return false;
            }
        });

    }

    private void filterList(String newText) {
         listFilter = new ArrayList<>();
        for(int i = 0; i < newsTrangChuList.size(); i++) {
            String title = newsTrangChuList.get(i).getTitle();
            String product_type = newsTrangChuList.get(i).getTenDanhMuc();

            if(title.toLowerCase().contains(newText.toLowerCase()) || product_type.toLowerCase().contains(newText.toLowerCase())){

                listFilter.add(new resultSearch(newsTrangChuList.get(i).getTitle() ,newsTrangChuList.get(i).getTime()
                , newsTrangChuList.get(i).getImage() , newsTrangChuList.get(i).getFee()));


            }
        }
        if(listFilter.isEmpty()) {
            Toast.makeText(mainActivity, "Mời bạn nhập thông tin tìm kiếm", Toast.LENGTH_SHORT).show();
        }else {
            rsSearchAdapter = new rsSearchAdapter(getContext() ,listFilter);
            rcvFilter.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvFilter.setAdapter(rsSearchAdapter);
            RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getContext() ,  DividerItemDecoration.VERTICAL);
            rcvFilter.addItemDecoration(decoration);
        }
    }

    private void clickSpinerLoaiTin() {
        product_type[] product_type = FragmentTrangChu.productType.getProductType();
        ArrayAdapter<product_type> adapter = new ArrayAdapter<product_type>(getContext(),
                android.R.layout.simple_spinner_item, product_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_phanLoaiTin.setAdapter(adapter);

        spn_phanLoaiTin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProgressDialog progressDialog = new ProgressDialog(mainActivity);
                progressDialog.setMessage("");
                progressDialog.show();
                getValuesSpinner();
                progressDialog.dismiss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getValuesSpinner() {
        loaiTin = spn_phanLoaiTin.getSelectedItem().toString();
        if (loaiTin.equals("Bất động sản")) {
            getListBDS();
        } else if (loaiTin.equals("Giải trí")) {
            getListGiaiTri();
        } else if (loaiTin.equals("Thời trang")) {

            getListThoitrang();
        }else {
            mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
            mNewsTrangChuAdapter.setDATA(newsTrangChuList);
            rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
        }
    }

    private void getListThoitrang() {
        List<NewsTrangChu> listfilter = new ArrayList<>();
        for (int i = 0; i < newsTrangChuList.size(); i++) {
            String title = newsTrangChuList.get(i).getTenDanhMuc();
            if (title.equals("Quần áo") || title.equals("Đồng hồ") || title.equals("Giày dép")
                    || title.equals("Túi xách") || title.equals("Nước hoa")
            ) {
                listfilter.add(new NewsTrangChu(newsTrangChuList.get(i).getTitle(), newsTrangChuList.get(i).getDescripsion()
                        , newsTrangChuList.get(i).getTime(), newsTrangChuList.get(i).getFee(), false, newsTrangChuList.get(i).getImage(), newsTrangChuList.get(i).getTenDanhMuc()));
            }
        }
        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
        mNewsTrangChuAdapter.setDATA(listfilter);
        rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
    }


    private void getListGiaiTri() {
        List<NewsTrangChu> listfilter = new ArrayList<>();
        for (int i = 0; i < newsTrangChuList.size(); i++) {
            String title = newsTrangChuList.get(i).getTenDanhMuc();
            if (title.equals("Nhạc cụ") || title.equals("Sách") || title.equals("Đồ thể thao, Dã ngoại")
                    || title.equals("Đồ sưu tầm  ,Đồ cổ") || title.equals("Thiết bị chơi game")
            ) {
                listfilter.add(new NewsTrangChu(newsTrangChuList.get(i).getTitle(), newsTrangChuList.get(i).getDescripsion()
                        , newsTrangChuList.get(i).getTime(), newsTrangChuList.get(i).getFee(), false, newsTrangChuList.get(i).getImage(), newsTrangChuList.get(i).getTenDanhMuc()));
            }
        }
        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
        mNewsTrangChuAdapter.setDATA(listfilter);
        rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);
    }

    private void getListBDS() {
        List<NewsTrangChu> listfilter = new ArrayList<>();
        for (int i = 0; i < newsTrangChuList.size(); i++) {
            String title = newsTrangChuList.get(i).getTenDanhMuc();
            if (title.equals("Chung cư") || title.equals("Nhà ở") || title.equals("Đất") || title.equals("Văn Phong")
                    || title.equals("Phòng trọ")) {
                listfilter.add(new NewsTrangChu(newsTrangChuList.get(i).getTitle(), newsTrangChuList.get(i).getDescripsion()
                        , newsTrangChuList.get(i).getTime(), newsTrangChuList.get(i).getFee(), false, newsTrangChuList.get(i).getImage(), newsTrangChuList.get(i).getTenDanhMuc()));
            }
        }
        mNewsTrangChuAdapter = new NewsTrangChuAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewsTrangChu.setLayoutManager(gridLayoutManager);
        mNewsTrangChuAdapter.setDATA(listfilter);
        rcvNewsTrangChu.setAdapter(mNewsTrangChuAdapter);

    }

    public static class productType {
        public static product_type[] getProductType() {
            product_type prd1 = new product_type("Tất cả");
            product_type prd2 = new product_type("Bất động sản");
            product_type prd3 = new product_type("Thời trang");
            product_type prd4 = new product_type("Giải trí");
            return new product_type[]{prd1, prd2, prd3, prd4};


        }
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
                    newsTrangChuList.add(new NewsTrangChu(giaiTriNews.getTitle(), giaiTriNews.getDescription(),
                            giaiTriNews.getDate(), giaiTriNews.getPrice(), true, "", giaiTriNews.getTenDanhMuc()));
                    mNewsTrangChuAdapter.notifyDataSetChanged();
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
                            thoiTrangNews.getDate(), thoiTrangNews.getPrice(), true, "", thoiTrangNews.getTenDanhMuc()));
                    mNewsTrangChuAdapter.notifyDataSetChanged();
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

                    newsTrangChuList.add(new NewsTrangChu(bdsNews.getTitle(), bdsNews.getDescription(),
                            bdsNews.getDate(), bdsNews.getPrice(), true, bdsNews.getImage(), bdsNews.getTenDanhMuc()));
                    mNewsTrangChuAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        newsTrangChuList.clear();
    }
}