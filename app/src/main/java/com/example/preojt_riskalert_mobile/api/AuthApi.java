package com.example.preojt_riskalert_mobile.api;

import com.example.preojt_riskalert_mobile.constants.ConstantApi;
import com.example.preojt_riskalert_mobile.models.request.SignInGoogleRequest;
import com.example.preojt_riskalert_mobile.models.response.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST(ConstantApi.SIGN_IN_GOOGLE)
    Call<AuthResponse> loginWithGoogle(@Body SignInGoogleRequest signInGoogleRequest);
}
