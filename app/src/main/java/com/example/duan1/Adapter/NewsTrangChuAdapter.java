package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1.R;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.NewsTrangChu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsTrangChuAdapter extends RecyclerView.Adapter<NewsTrangChuAdapter.ViewHolder> {private Context context;
    private List<NewsTrangChu> newsTrangChuList;
    public NewsTrangChuAdapter(Context context) {
        this.context = context;
    }

    public void setDATA(List<NewsTrangChu> x) {
        this.newsTrangChuList = x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsTrangChuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_trangchu_news,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsTrangChuAdapter.ViewHolder h, int position) {
        NewsTrangChu OOP = newsTrangChuList.get(position);
        if(OOP == null){
            return;
        }
        h.tvTitle.setText(OOP.getTitle());
        String free = String.valueOf(OOP.getFee());
        h.tvFee.setText(free);
        h.tvTime.setText(OOP.getTime());
        h.tvDes.setText(OOP.getDescripsion());
        String strimage = OOP.getImage();
        try {
            Picasso.with(context)
                    .load(strimage)
                    .into(h.imgNews);
        }catch (Exception e) {
            System.out.println("Lỗi load ảnh trong newsAdapter" + e);
        }

//        if(OOP.getsoluonganh() > 1){
//            h.linearLayout.setVisibility(View.VISIBLE);
//            h.tvSLAnh.setText(OOP.getsoluonganh());
//        }else{
//            h.linearLayout.setVisibility(View.INVISIBLE);
//        }

//        Glide.with(context).load(OOP.getImage()).into(h.imgNews);

        if(OOP.isFavorite()){
            h.imgFavorite.setImageResource(R.drawable.ic_item_trangchu_favorite);
        }else{
            h.imgFavorite.setImageResource(R.drawable.ic_item_trangchu_unfavorite);
        }
        h.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OOP.isFavorite()){
                    OOP.setFavorite(false);
                }else{
                    OOP.setFavorite(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(newsTrangChuList != null){
            return newsTrangChuList.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSLAnh,tvTitle,tvFee,tvDes,tvTime;
        private ImageView imgNews,imgFavorite,imgLoaiTin;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSLAnh = itemView.findViewById(R.id.tv_item_trangchu_news_SoLuongAnh);
            tvDes = itemView.findViewById(R.id.tv_item_trangchu_descripsion);
            tvFee = itemView.findViewById(R.id.tv_item_trangchu_fee);
            tvTime = itemView.findViewById(R.id.tv_item_trangchu_news_Time);
            tvTitle = itemView.findViewById(R.id.tv_item_trangchu_title);

            imgFavorite = itemView.findViewById(R.id.img_item_trangchu_news_favorite);
            imgNews = itemView.findViewById(R.id.img_item_trangchu_news_Pic);
            imgLoaiTin = itemView.findViewById(R.id.img_item_trangchu_news_LoaiTin);

            linearLayout = itemView.findViewById(R.id.layout_item_trangchu_news_SLA);
        }
    }
}

