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
import com.example.duan1.model.resultSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class rsSearchAdapter extends RecyclerView.Adapter<rsSearchAdapter.ViewHolder> {
    private Context context;
    private List<resultSearch> listRs ;

    public rsSearchAdapter(Context context, List<resultSearch> listRs) {
        this.context = context;
        this.listRs = listRs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result_search, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        resultSearch resultSearch = listRs.get(position);
        h.title.setText(resultSearch.getTitle());
        h.date.setText(resultSearch.getDate());
        String price =String.valueOf(resultSearch.getPrice());
        h.price.setText(price);
        String strimage = resultSearch.getImage();
        try {
            Picasso.with(context)
                    .load(strimage)
                    .into(h.image);
        }catch (Exception e) {
            System.out.println("Lỗi load ảnh trong newsAdapter" + e);
        }

    }

    @Override
    public int getItemCount() {
        if(listRs != null) {
            return listRs.size();
        }else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title ,price, date;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                title = itemView.findViewById(R.id.tvTitle_rs_search) ;
                price = itemView.findViewById(R.id.tvPrice_rs_search);
                date = itemView.findViewById(R.id.tvDate_rs_search);
                image = itemView.findViewById(R.id.img_rs_serach);
        }
    }
}
