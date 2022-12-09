package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.dagTinGiaiTriActivity;
import com.example.duan1.dangTinBDSActivity;
import com.example.duan1.dangTinBDSDatActivity;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.historyNews;
import com.example.duan1.model.thoiTrangNews;
import com.example.duan1.suaTinPhongTroActivity;
import com.example.duan1.suaTinThoiTrangActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class historyNewsAdapter extends RecyclerView.Adapter<historyNewsAdapter.HolderView>  {
    private Context mContext;
    private List<historyNews> listHistory;
    private List<String> listChild = new ArrayList<>();
    private List<String> listChild1 = new ArrayList<>();
    private List<String> listChild2 = new ArrayList<>();
    private String title_News ;
    private int id;

    DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
    DatabaseReference myData1 = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
    DatabaseReference myData2 = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");

    public historyNewsAdapter(Context mContext, List<historyNews> listHistory) {
        this.mContext = mContext;
        this.listHistory =listHistory;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_tin_dang, parent, false);

        return new HolderView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder,final int position) {
        historyNews giaiTriNews =  listHistory.get(position);
        String tenDanhMuc = giaiTriNews.getTenDanhMuc();
        String Title_Post = giaiTriNews.getTitle_historyNews();
        listChild1();
        listChild2();
        listChild3();
        holder.title_historyNews.setText(giaiTriNews.getTitle_historyNews());
        holder.desc_historyNews.setText(giaiTriNews.getDesc_historyNews());
        holder.time_historyNews.setText("12-12-2002");
        holder.icon_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XoaTin(giaiTriNews.getTitle_historyNews());


            }
        });
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tenDanhMuc.equals("Quần áo") || tenDanhMuc.equals("Đồng hồ") || tenDanhMuc.equals("Giày dép") ||tenDanhMuc.equals("Túi xách")
                || tenDanhMuc.equals("Nước hoa") ){
                    Intent intent = new Intent(mContext , suaTinThoiTrangActivity.class);
                    intent.putExtra("title" , Title_Post);
                    intent.putExtra("tenDanhMuc" , tenDanhMuc);
                    mContext.startActivity(intent);
                }else if(tenDanhMuc.equals("Phòng trọ")) {
                    Intent intent = new Intent(mContext , suaTinPhongTroActivity.class);
                    intent.putExtra("title" , Title_Post);
                    intent.putExtra("tenDanhMuc" , tenDanhMuc);
                    mContext.startActivity(intent);
                }else if(tenDanhMuc.equals("Đất")) {
                    Intent intent = new Intent(mContext , dangTinBDSDatActivity.class);
                    intent.putExtra("title" , Title_Post);
                    intent.putExtra("tenDanhMuc" , tenDanhMuc);
                    mContext.startActivity(intent);
                }else if(tenDanhMuc.equals("Văn Phòng")
                        || tenDanhMuc.equalsIgnoreCase("Nhà ở")
                        || tenDanhMuc.equalsIgnoreCase("Chung cư")){
                    Intent intent = new Intent(mContext , dangTinBDSActivity.class);
                    intent.putExtra("title" , Title_Post);
                    intent.putExtra("tenDanhMuc" , tenDanhMuc);
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext , dagTinGiaiTriActivity.class);
                    intent.putExtra("title" , Title_Post);
                    intent.putExtra("tenDanhMuc" , tenDanhMuc);
                    mContext.startActivity(intent);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        if (listHistory != null) {
            return listHistory.size();
        } else {
            return 0;
        }
    }
private void XoaTin(String title){
    for(int i = 0 ; i < listChild.size() ;i++) {

        myData.child(listChild.get(i)).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                giaiTriNews giaiTriNew = snapshot.getValue(giaiTriNews.class);
                if(giaiTriNew.getTitle().equals(title)){
                    String id = String.valueOf(giaiTriNew.getId());
                    String tenDanhMuc = String.valueOf(giaiTriNew.getTenDanhMuc());

                        myData.child(tenDanhMuc).child(id).removeValue();
                        notifyDataSetChanged();
                        Toast.makeText(mContext.getApplicationContext(), "Đã xóa Tin", Toast.LENGTH_SHORT).show();

                }
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

    for (int i = 0; i< listChild2.size() ;i++) {
        myData1.child(listChild2.get(i)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BDSNews bdsNews = snapshot.getValue(BDSNews.class);
                if(bdsNews.getTitle().equals(title)){
                    String id = String.valueOf(bdsNews.getId());
                    String tenDanhMuc = String.valueOf(bdsNews.getTenDanhMuc());
                    
                    myData1.child(tenDanhMuc).child(id).removeValue();
                    notifyDataSetChanged();
                    Toast.makeText(mContext.getApplicationContext(), "Đã xóa Tin", Toast.LENGTH_SHORT).show();

                }

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

    for(int i = 0; i < listChild1.size();i++){
        myData2.child(listChild1.get(i)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                thoiTrangNews thoiTrangNews = snapshot.getValue(com.example.duan1.model.thoiTrangNews.class);
                if(thoiTrangNews.getTitlePost().equals(title)){
                    String id = String.valueOf(thoiTrangNews.getId());
                    String tenDanhMuc = String.valueOf(thoiTrangNews.getTenDanhMuc());
                    
                    myData2.child(tenDanhMuc).child(id).removeValue();
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                }

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




    public class HolderView extends RecyclerView.ViewHolder {
        private TextView title_historyNews, desc_historyNews, time_historyNews;
        private ImageView icon_option;
    private RelativeLayout layout_item;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            title_historyNews = itemView.findViewById(R.id.title_historyNews);
            desc_historyNews = itemView.findViewById(R.id.desc_historyNews);
            time_historyNews = itemView.findViewById(R.id.time_historyNews);
            icon_option = itemView.findViewById(R.id.icon_option);
            layout_item = itemView.findViewById(R.id.layout_item);

        }




    }



    private void suaTin() {
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
