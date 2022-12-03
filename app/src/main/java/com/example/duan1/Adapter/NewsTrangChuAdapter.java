package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1.R;
import com.example.duan1.model.NewsTrangChu;

import java.util.List;


public class NewsTrangChuAdapter extends RecyclerView.Adapter<NewsTrangChuAdapter.ViewHolder> {

    private Context context;
    private List<NewsTrangChu> newsTrangChuList;

    public NewsTrangChuAdapter(Context context) {
        this.context = context;
    }

    public void setDATA(List<NewsTrangChu> newsTrangChuList) {
        this.newsTrangChuList = newsTrangChuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsTrangChuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trangchu_news,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsTrangChuAdapter.ViewHolder h, int position) {
        NewsTrangChu OOP = newsTrangChuList.get(position);
        if(OOP == null){
            return;
        }
        h.tvTitle.setText(OOP.getTitle());
        h.tvFee.setText(OOP.getFee());
        h.tvTime.setText(OOP.getTime());
        h.tvDes.setText(OOP.getDescripsion());
        h.tvSLAnh.setText(OOP.getsoluonganh());

        Glide.with(context).load(OOP.getArrURL()).into(h.imgNews);

        if(OOP.isFavorite()){
            h.imgFavorite.setImageResource(R.drawable.ic_item_trangchu_favorite);
        }else{
            h.imgFavorite.setImageResource(R.drawable.ic_item_trangchu_unfavorite);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSLAnh,tvTitle,tvFee,tvDes,tvTime;
        private ImageView imgNews,imgFavorite,imgLoaiTin;

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

        }
    }
}
