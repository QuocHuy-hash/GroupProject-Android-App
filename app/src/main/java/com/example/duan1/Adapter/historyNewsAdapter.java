package com.example.duan1.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.historyNews;

import java.util.List;

public class historyNewsAdapter extends RecyclerView.Adapter<historyNewsAdapter.HolderView> {
    private Context mContext;
    private List<historyNews> listHistory;

    public historyNewsAdapter(Context mContext, List<historyNews> listHistory) {
        this.mContext = mContext;
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_tin_dang, parent, false);

        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        historyNews giaiTriNews = listHistory.get(position);

        holder.title_historyNews.setText(giaiTriNews.getTitle_historyNews());
        holder.desc_historyNews.setText(giaiTriNews.getDesc_historyNews());
        holder.time_historyNews.setText("12-12-2002");
        holder.icon_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogOption();
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
    private void showDialogOption() {

    }

    public class HolderView extends RecyclerView.ViewHolder {
        private TextView title_historyNews, desc_historyNews, time_historyNews;
        private ImageView icon_option;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            title_historyNews = itemView.findViewById(R.id.title_historyNews);
            desc_historyNews = itemView.findViewById(R.id.desc_historyNews);
            time_historyNews = itemView.findViewById(R.id.time_historyNews);
            icon_option = itemView.findViewById(R.id.icon_option);
        }
    }
}
