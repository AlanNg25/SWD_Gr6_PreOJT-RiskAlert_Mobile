package com.example.preojt_riskalert_mobile.api;

import com.example.preojt_riskalert_mobile.constants.Constant;
import com.example.preojt_riskalert_mobile.constants.ConstantApi;
import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationApi {
    @GET(ConstantApi.GET_NOTIFICATION)
    Call<List<NotificationResponse>> getNotifications(@Path("id") String userId);
}
