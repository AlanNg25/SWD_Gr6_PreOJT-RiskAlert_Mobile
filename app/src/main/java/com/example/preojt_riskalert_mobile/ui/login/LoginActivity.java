


package com.example.preojt_riskalert_mobile.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preojt_riskalert_mobile.MainActivity;
import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.api.AuthApi;
import com.example.preojt_riskalert_mobile.interfaces.auth.AuthViewImpl;
import com.example.preojt_riskalert_mobile.models.request.SignInGoogleRequest;
import com.example.preojt_riskalert_mobile.models.response.AuthResponse;
import com.example.preojt_riskalert_mobile.presenters.AuthPresenter;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements AuthViewImpl {

    private static final String TAG = "LoginGoogle";
    private AuthPresenter mAuthPresenter;
    private AuthApi mAuthApi;
    private Button btnLoginFEID;

    private GoogleSignInClient mGoogleSignInClient;

    // Khai báo launcher mới để thay cho onActivityResult
    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                } else {
                    Log.d(TAG, ": " + result.getResultCode());
                    Toast.makeText(this, "Đăng nhập" + result.getResultCode(), Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.jwt_token_name), null);
        if (token != null && !token.isEmpty()) {
            // Token còn, chuyển vào MainActivity luôn
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // kết thúc LoginActivity
            return;
        }

        setContentView(R.layout.activity_login);

        btnLoginFEID = findViewById(R.id.btnLoginFEID);
        btnLoginFEID.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, LoginWithFEIDActivity.class);
            startActivity(intent);
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)) // Web client ID
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
        btnLoginGoogle.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                String email = account.getEmail();
                String name = account.getDisplayName();
                String idToken = account.getIdToken();

                Log.d(TAG, "Login successful: " + idToken);
                if (idToken == null) {
                    Log.w(TAG, "ID Token is null! Kiểm tra lại Web Client ID.");
                }

                Toast.makeText(this, "Đăng nhập thành công: " + name, Toast.LENGTH_SHORT).show();
                SignInGoogleRequest signInGoogleRequest = new SignInGoogleRequest(idToken);
                mAuthApi = RetrofitClient.getAuthApi(this);
                mAuthPresenter = new AuthPresenter(this, this);
                mAuthPresenter.signInWithGoogle(signInGoogleRequest);
            }

        } catch (ApiException e) {
            Log.e(TAG, "Đăng nhập Google thất bại. Mã lỗi: " + e.getStatusCode(), e);
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSignInSuccess(AuthResponse authResponse) {
        SharedPreferences sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("jwt_token", authResponse.getToken()); // token là chuỗi JWT bạn nhận được từ backend
//        editor.apply();
        JwtUtil.SaveJwtTokenToSharedPreferences(authResponse.getToken(), sharedPreferences);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSignInFailure(String code, String errorMessage) {
        Log.d(TAG, "onSignInFailure: " + code + " - " + errorMessage);
    }

    @Override
    public void onSignInByEmailSuccess(AuthResponse authResponse) {

    }

    @Override
    public void onSignInByEmailFailure(String code, String errorMessage) {

    }

    @Override
    public void onLogoutSuccess() {

    }

    @Override
    public void onLogoutFailure(String code, String errorMessage) {

    }
}

