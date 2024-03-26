package com.example.hairsara.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairsara.R;
import com.example.hairsara.Request.ChiNhanhRequest;

import java.util.ArrayList;
import java.util.List;

public class ChiNhanhAdapter extends RecyclerView.Adapter<ChiNhanhAdapter.ViewHolder> {
    private List<ChiNhanhRequest> chiNhanhList= new ArrayList<>();

    // Constructor và phương thức cần thiết

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view cho mỗi item trong RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dichvu_data, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Hiển thị dữ liệu lên item
        ChiNhanhRequest dichVu = chiNhanhList.get(position);
        holder.tenChiNhanhTextView.setText(dichVu.getTenChiNhanh());
        holder.diaChiTextView.setText(dichVu.getDiaChi());
    }

    @Override
    public int getItemCount() {
        return chiNhanhList != null ? chiNhanhList.size() : 0;
    }


    public void setchiNhanhList(List<ChiNhanhRequest> chiNhanhList) {
        this.chiNhanhList = chiNhanhList;
    }

    // Lớp ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tenChiNhanhTextView;
        public TextView diaChiTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenChiNhanhTextView = itemView.findViewById(R.id.tv_Title);
            diaChiTextView = itemView.findViewById(R.id.tv_Body);
        }
    }
}
