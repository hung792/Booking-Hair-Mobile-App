package com.example.hairsara.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairsara.Adapter.ChiNhanhAdapter;
import com.example.hairsara.Adapter.DichVuAdapter;
import com.example.hairsara.Interface.ChiNhanh;
import com.example.hairsara.Interface.DichVu;
import com.example.hairsara.Interface.Login;
import com.example.hairsara.LoginActivity;
import com.example.hairsara.R;
import com.example.hairsara.Request.ChiNhanhRequest;
import com.example.hairsara.Request.DichVuRequest;
import com.example.hairsara.TrustAllCerts;
import com.example.hairsara.databinding.FragmentDashboardBinding;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private DichVuAdapter dichVuAdapter;
    private DichVu apiService;

    private RecyclerView recyclerView1;
    private ChiNhanhAdapter chiNhanhAdapter;
    private ChiNhanh apiService1;

    // ... Các biến khác và phương thức

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.rcvDichvu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dichVuAdapter = new DichVuAdapter();
        recyclerView.setAdapter(dichVuAdapter);

        recyclerView1 = view.findViewById(R.id.rcv_chinhanh);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        chiNhanhAdapter = new ChiNhanhAdapter();
        recyclerView1.setAdapter(chiNhanhAdapter);

        // Khởi tạo ApiService
        apiService = retrofit.create(DichVu.class);
        apiService1 = retrofit.create(ChiNhanh.class);
        // Gọi API và cập nhật dữ liệu trong Adapter
        loadDataFromApi1();
        loadDataFromApi2();
        return view;
    }

    private void loadDataFromApi1() {
        Call<List<DichVuRequest>> call = apiService.getListDichVu();
        call.enqueue(new Callback<List<DichVuRequest>>() {
            @Override
            public void onResponse(Call<List<DichVuRequest>> call, Response<List<DichVuRequest>> response) {
                if (response.isSuccessful()) {
                    // Xử lý phản hồi thành công
                    List<DichVuRequest> dichVuList = response.body();
                    dichVuAdapter.setDichVuList(dichVuList);
                    dichVuAdapter.notifyDataSetChanged(); // Đảm bảo gọi thông báo thay đổi ở đây
                } else {
                    showAlertDialog("API Call Failed", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DichVuRequest>> call, Throwable t) {
                showAlertDialog("API Call Failed", "Error: " + t.getMessage());
            }
        });
    }
    private void loadDataFromApi2() {
        Call<List<ChiNhanhRequest>> call = apiService1.getListChiNhanh();
        call.enqueue(new Callback<List<ChiNhanhRequest>>() {
            @Override
            public void onResponse(Call<List<ChiNhanhRequest>> call, Response<List<ChiNhanhRequest>> response) {
                if (response.isSuccessful()) {
                    // Xử lý phản hồi thành công
                    List<ChiNhanhRequest> chiNhanhList = response.body();
                    chiNhanhAdapter.setchiNhanhList(chiNhanhList);
                    chiNhanhAdapter.notifyDataSetChanged(); // Đảm bảo gọi thông báo thay đổi ở đây
                } else {
                    showAlertDialog("API Call Failed", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ChiNhanhRequest>> call, Throwable t) {
                showAlertDialog("API Call Failed", "Error: " + t.getMessage());
            }
        });
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://192.168.1.17:7271/")
            .client(new OkHttpClient.Builder()
                    .sslSocketFactory(getSSLSocketFactory(), new TrustAllCerts())
                    .hostnameVerifier((hostname, session) -> true)
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}