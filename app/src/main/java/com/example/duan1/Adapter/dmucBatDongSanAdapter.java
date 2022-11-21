package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.danhMucBatDongSan;

import java.util.List;

public class dmucBatDongSanAdapter extends RecyclerView.Adapter<dmucBatDongSanAdapter.ViewHolder> {
    private Context context;
    private List<danhMucBatDongSan> listDmBDS;

    public dmucBatDongSanAdapter(Context context, List<danhMucBatDongSan> listDmBDS) {
        this.context = context;
        this.listDmBDS = listDmBDS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danh_muc_bds, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        danhMucBatDongSan dmBDS = listDmBDS.get(position);
        if(dmBDS == null) {
            return;
        }
        holder.tvTenDanhMuc.setText(dmBDS.getTenDanhMuc());
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "danh mục : " + dmBDS.getTenDanhMuc() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listDmBDS != null) {
            return listDmBDS.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTenDanhMuc;
            LinearLayout layout_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            tvTenDanhMuc = itemView.findViewById(R.id.tvTenDanhMuc);
        }
    }
}
