package com.example.preojt_riskalert_mobile.interfaces.auth;

import com.example.preojt_riskalert_mobile.models.request.SignInByEmailRequest;
import com.example.preojt_riskalert_mobile.models.request.SignInGoogleRequest;

public interface AuthPresenterImpl {
    void signInWithGoogle(SignInGoogleRequest signInGoogleRequest);

    void signInByEmail(SignInByEmailRequest signInByEmailRequest);
}
