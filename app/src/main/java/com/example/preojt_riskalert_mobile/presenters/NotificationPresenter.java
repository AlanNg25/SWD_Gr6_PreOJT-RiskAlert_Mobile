package com.example.preojt_riskalert_mobile.presenters;

import android.content.Context;

import com.example.preojt_riskalert_mobile.api.NotificationApi;
import com.example.preojt_riskalert_mobile.interfaces.notification.NotificationPresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.notification.NotificationViewImpl;
import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements NotificationPresenterImpl {
    private static final String TAG = "NotificationPresenter";

    // Assuming you have a NotificationApi similar to GradeApi
    private NotificationApi mNotificationApi;
    private NotificationViewImpl mNotificationView;

    public NotificationPresenter(NotificationViewImpl notificationView, Context context) {
        this.mNotificationView = notificationView;
        if(mNotificationApi == null) {
            mNotificationApi = RetrofitClient.getNotificationApi(context);
        }
    }
    @Override
    public void getNotificationsByUserId(String userId) {
        mNotificationApi.getNotifications(userId).enqueue(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(Call<List<NotificationResponse>> call, Response<List<NotificationResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<NotificationResponse> notificationResponses = response.body();
                    mNotificationView.onGetNotificationsByUserIdSuccess(notificationResponses);
                } else {
                    mNotificationView.onGetNotificationsByUserIdFail("Failed to retrieve notifications: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponse>> call, Throwable t) {
                mNotificationView.onGetNotificationsByUserIdFail("Failed to retrieve notifications: " + t.getMessage());
            }
        });

    }
}
