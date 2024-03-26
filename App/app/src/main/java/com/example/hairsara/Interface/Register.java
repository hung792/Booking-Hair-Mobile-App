package com.example.hairsara.Interface;

import com.example.hairsara.Models.ApiResponse;
import com.example.hairsara.Request.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Register {
    @POST("Account/Authentication/Register")
    Call<ApiResponse> register(@Body RegisterRequest request);
}
