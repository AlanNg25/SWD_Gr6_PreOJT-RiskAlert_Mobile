package com.example.preojt_riskalert_mobile.interfaces.notification;

import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;

import java.util.List;

public interface NotificationViewImpl {
    void onGetNotificationsByUserIdSuccess(List<NotificationResponse> notificationResponses);

    void onGetNotificationsByUserIdFail(String message);

}
