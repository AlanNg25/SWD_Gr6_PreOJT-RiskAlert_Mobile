package com.example.preojt_riskalert_mobile.presenters;

import android.content.Context;
import android.util.Log;

import com.example.preojt_riskalert_mobile.api.AuthApi;
import com.example.preojt_riskalert_mobile.interfaces.auth.AuthPresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.auth.AuthViewImpl;
import com.example.preojt_riskalert_mobile.models.request.SignInGoogleRequest;
import com.example.preojt_riskalert_mobile.models.response.AuthResponse;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter implements AuthPresenterImpl {
    private static final String TAG = "AuthPresenter";
    private AuthViewImpl mAuthView;
    private AuthApi mAuthApi;

    public AuthPresenter(AuthViewImpl authView, Context context) {
        this.mAuthView = authView;
        if (mAuthApi == null) {
            mAuthApi = RetrofitClient.getAuthApi(context);
        }
    }

    @Override
    public void signInWithGoogle(SignInGoogleRequest signInGoogleRequest) {
        mAuthApi.loginWithGoogle(signInGoogleRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200) {
                        mAuthView.onSignInSuccess(response.body());
                        Log.d(TAG, "onResponse: " + response.body().getToken());
                    } else {
                        mAuthView.onSignInFailure(response.code() + "", response.message());
                    }
                } else {
                    mAuthView.onSignInFailure(response.code() + "", response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                mAuthView.onSignInFailure("500", t.getMessage());
            }
        });
    }
}
