package com.example.preojt_riskalert_mobile.ui.notifications;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.preojt_riskalert_mobile.interfaces.notification.NotificationViewImpl;
import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;
import com.example.preojt_riskalert_mobile.presenters.NotificationPresenter;

import java.util.List;

public class NotificationsViewModel extends ViewModel implements NotificationViewImpl {

    private final MutableLiveData<List<NotificationResponse>> notifications = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private NotificationPresenter presenter;

    public void init(Context context) {
        if (presenter == null) {
            presenter = new NotificationPresenter(this, context);
        }
    }

    public void loadNotifications(String userId) {
        if (presenter != null) {
            presenter.getNotificationsByUserId(userId);
        }
    }

    public LiveData<List<NotificationResponse>> getNotifications() {
        return notifications;
    }

    public LiveData<String> getError() {
        return error;
    }

    @Override
    public void onGetNotificationsByUserIdSuccess(List<NotificationResponse> notificationResponses) {
        notifications.postValue(notificationResponses);
    }

    @Override
    public void onGetNotificationsByUserIdFail(String message) {
        error.postValue(message);
    }
}
