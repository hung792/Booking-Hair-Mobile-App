package com.example.hairsara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hairsara.Interface.Login;
import com.example.hairsara.Models.ApiResponse;
import com.example.hairsara.Request.LoginRequest;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Login logininterface;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Khởi tạo views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        TextView btnRegister = findViewById(R.id.textviewSignUp);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        // Initialize logininterface
        logininterface = retrofit.create(Login.class);

        // Kiểm tra nếu loginButton không null trước khi thêm OnClickListener
        if (loginButton != null) {
            loginButton.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Call<ApiResponse> call = logininterface.login(new LoginRequest(username, password));
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            // Xử lý phản hồi thành công
                            ApiResponse apiResponse = response.body();
                            String token = apiResponse.getToken();
                            String expiration = apiResponse.getExpiration();

                            SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showAlertDialog("Login Failed", "Invalid username or password");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        showAlertDialog("Login Failed", "Error: " + t.getMessage());
                    }
                });
            });
        }
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
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