package com.example.preojt_riskalert_mobile.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preojt_riskalert_mobile.MainActivity;
import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.interfaces.auth.AuthViewImpl;
import com.example.preojt_riskalert_mobile.models.request.SignInByEmailRequest;
import com.example.preojt_riskalert_mobile.models.response.AuthResponse;
import com.example.preojt_riskalert_mobile.presenters.AuthPresenter;

public class LoginWithFEIDActivity extends AppCompatActivity implements AuthViewImpl {

    private EditText edEmail;
    private EditText edPassword;
    private Button btnLogin;
    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_feidactivity);

        // Ánh xạ view
        edEmail = findViewById(R.id.etEmail);
        edPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Khởi tạo presenter
        authPresenter = new AuthPresenter(this, this);

        // Sự kiện nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (email.isEmpty()) {
                    edEmail.setError("Email is required");
                    return;
                }
                if (password.isEmpty()) {
                    edPassword.setError("Password is required");
                    return;
                }

                // Tạo request object
                SignInByEmailRequest request = new SignInByEmailRequest(email, password);

                // Gọi presenter xử lý đăng nhập
                authPresenter.signInByEmail(request);
            }
        });
    }

    @Override
    public void onSignInSuccess(AuthResponse authResponse) {
        // Không dùng trong login FEID
    }

    @Override
    public void onSignInFailure(String code, String errorMessage) {
        // Không dùng trong login FEID
    }

    @Override
    public void onSignInByEmailSuccess(AuthResponse authResponse) {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", authResponse.getToken()); // token là chuỗi JWT bạn nhận được từ backend
        editor.apply();
        Intent intent = new Intent(LoginWithFEIDActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSignInByEmailFailure(String code, String errorMessage) {
        Toast.makeText(this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
