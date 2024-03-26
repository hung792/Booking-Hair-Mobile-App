package com.example.hairsara;

import static com.example.hairsara.R.id.registerButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairsara.Interface.Login;
import com.example.hairsara.Interface.Register;
import com.example.hairsara.Models.ApiResponse;
import com.example.hairsara.Request.LoginRequest;
import com.example.hairsara.Request.RegisterRequest;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Register registerinterface;
    private EditText newUsernameEditText;
    private EditText newPasswordEditText;
    private EditText newEmailEditText;
    private Button registerButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Khởi tạo views
        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        newEmailEditText = findViewById(R.id.newEmailEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Initialize logininterface
        registerinterface = retrofit.create(Register.class);

        // Kiểm tra nếu loginButton không null trước khi thêm OnClickListener
        if (registerButton != null) {
            registerButton.setOnClickListener(v -> {
                String username = newUsernameEditText.getText().toString();
                String password = newPasswordEditText.getText().toString();
                String email = newEmailEditText.getText().toString();
                Call<ApiResponse> call = registerinterface.register(new RegisterRequest(username, email, password));
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

                            Intent intent = new Intent(RegisterActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showAlertDialog("Register Failed", "Invalid username or password");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        showAlertDialog("Register Failed", "Error: " + t.getMessage());
                    }
                });
            });
        }
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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