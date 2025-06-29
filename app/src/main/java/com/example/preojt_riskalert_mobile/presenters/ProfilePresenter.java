package com.example.preojt_riskalert_mobile.presenters;

import android.content.Context;

import com.example.preojt_riskalert_mobile.api.ProfileApi;
import com.example.preojt_riskalert_mobile.interfaces.profile.ProfilePresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.profile.ProfileViewImpl;
import com.example.preojt_riskalert_mobile.models.response.ProfileResponse;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfilePresenterImpl {
    private ProfileViewImpl mProfileView;
    private ProfileApi mProfileApi;

    public ProfilePresenter(ProfileViewImpl profileView, Context context) {
        this.mProfileView = profileView;
        if (mProfileApi == null) {
            mProfileApi = RetrofitClient.getProfileApi(context);
        }

    }

    @Override
    public void getProfileById(String userId) {
        mProfileApi.getProfile(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200) {
                        mProfileView.onProfileSuccess(response.body());
                    } else {
                        mProfileView.onProfileError(response.code() + ": " + response.message());
                    }
                } else {
                    mProfileView.onProfileError(response.code() + ": " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mProfileView.onProfileError("500: " + t.getMessage());
            }
        });
    }
}
