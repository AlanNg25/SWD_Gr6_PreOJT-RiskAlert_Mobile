package com.example.preojt_riskalert_mobile.interfaces.auth;

import com.example.preojt_riskalert_mobile.models.response.AuthResponse;

public interface AuthViewImpl {
    void onSignInSuccess(AuthResponse authResponse);
    void onSignInFailure(String code, String errorMessage);

    void onSignInByEmailSuccess(AuthResponse authResponse);
    void onSignInByEmailFailure(String code, String errorMessage);
}
