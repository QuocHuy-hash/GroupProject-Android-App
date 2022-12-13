package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.chitietdacdiem;

import java.util.List;

public class chitietDacDiemAdapter extends RecyclerView.Adapter<chitietDacDiemAdapter.ViewHolder> {
    private Context context;
    private List<chitietdacdiem> chitietdacdiemList;

    public chitietDacDiemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<chitietdacdiem> x) {
        this.chitietdacdiemList = x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_chitiet_dacdiem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        chitietdacdiem OOP = chitietdacdiemList.get(position);
        if(OOP == null){
            return;
        }
//        set image bằng if else lấy thẳng id để set khi lấy xuống rồi set src
        if(OOP.getName().equals("tendacdiem")){
            h.imgDacdiem.setImageResource(R.drawable.ic_item_trangchu_favorite);
        }
//
        h.mota.setText(OOP.getMota());
    }

    @Override
    public int getItemCount() {
        if(chitietdacdiemList != null){
            return chitietdacdiemList.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView mota;
    private ImageView imgDacdiem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDacdiem = itemView.findViewById(R.id.img_chitiet_dacdiem_item);
            mota = itemView.findViewById(R.id.tv_chitiet_dacdiem_item);
        }
    }
}
