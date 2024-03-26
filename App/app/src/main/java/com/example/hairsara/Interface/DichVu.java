package com.example.hairsara.Interface;

import com.example.hairsara.Request.ChiNhanhRequest;
import com.example.hairsara.Request.DichVuRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DichVu {
    // Định nghĩa phương thức GET cho DichVu
    @GET("DichVu/ListDichVu")  // Thay "your_path_here" bằng đường dẫn thích hợp của bạn
    Call<List<DichVuRequest>> getListDichVu();



    // Nếu cần thêm các tham số, bạn có thể thêm chúng vào đây
    // Ví dụ: @GET("your_path_here")
    // Call<ApiResponse> getData(@Query("param1") String param1, @Query("param2") int param2);
}
