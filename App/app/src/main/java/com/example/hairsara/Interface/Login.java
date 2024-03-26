package com.example.hairsara.Interface;

import com.example.hairsara.Models.ApiResponse;
import com.example.hairsara.Request.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Login {
    @POST("Account/Authentication/Login")
    Call<ApiResponse> login(@Body LoginRequest request);
}
