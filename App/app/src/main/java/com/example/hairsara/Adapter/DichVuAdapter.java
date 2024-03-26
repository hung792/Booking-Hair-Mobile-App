package com.example.hairsara.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairsara.R;
import com.example.hairsara.Request.DichVuRequest;

import java.util.ArrayList;
import java.util.List;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.ViewHolder> {
    private List<DichVuRequest> dichVuList= new ArrayList<>();

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
        DichVuRequest dichVu = dichVuList.get(position);
        holder.tenDichVuTextView.setText(dichVu.getTenDichVu());
        holder.moTaTextView.setText(dichVu.getMoTa());
    }

    @Override
    public int getItemCount() {
        return dichVuList != null ? dichVuList.size() : 0;
    }


    public void setDichVuList(List<DichVuRequest> dichVuList) {
        this.dichVuList = dichVuList;
    }

    // Lớp ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tenDichVuTextView;
        public TextView moTaTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDichVuTextView = itemView.findViewById(R.id.tv_Title);
            moTaTextView = itemView.findViewById(R.id.tv_Body);
        }
    }
}
