package com.example.preojt_riskalert_mobile.api;

import com.example.preojt_riskalert_mobile.models.response.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileApi {
    @GET("User/{id}")
    Call<ProfileResponse> getProfile(@Path("id") String userId);
}
