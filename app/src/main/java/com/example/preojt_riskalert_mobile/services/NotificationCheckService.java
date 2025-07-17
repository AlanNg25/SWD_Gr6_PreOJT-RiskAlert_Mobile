package com.example.preojt_riskalert_mobile.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.interfaces.notification.NotificationViewImpl;
import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;
import com.example.preojt_riskalert_mobile.presenters.NotificationPresenter;
import com.example.preojt_riskalert_mobile.ui.notifications.NotificationsFragment;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;

import java.util.List;

public class NotificationCheckService extends Service implements NotificationViewImpl {

    private static final String TAG = "NotificationCheckSvc";
    private static final String CHANNEL_ID = "notification_check_channel_v2";
    private static final int INTERVAL_MS = 10000; // 30s
    private static final int NOTIFICATION_ID = 1001;

    private Handler handler;
    private Runnable pollingRunnable;
    private NotificationPresenter presenter;

    private int lastNotificationCount = 0;
    private SharedPreferences sharedPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = new NotificationPresenter(this, getApplicationContext());

        sharedPrefs = getSharedPreferences("notification_prefs", MODE_PRIVATE);
        lastNotificationCount = sharedPrefs.getInt("last_notification_count", 0);

        handler = new Handler();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = createServiceNotification("Đang kiểm tra thông báo...");
        startForeground(NOTIFICATION_ID, notification);

        // Lấy user ID từ token hoặc từ intent
        String currentUserId = null;
        SharedPreferences authPrefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        String token = authPrefs.getString("jwt_token", null);
        if (token != null) {
            currentUserId = JwtUtil.getSubFromToken(token);
        }
        Log.d(TAG, "onStartCommand: " + currentUserId);
        String userIdFromIntent = intent.getStringExtra("userId");
        if (userIdFromIntent != null) {
            currentUserId = userIdFromIntent;
        }

        if (currentUserId != null) {
            String finalCurrentUserId = currentUserId;
            pollingRunnable = new Runnable() {
                @Override
                public void run() {
                    if (presenter != null) {
                        Log.d(TAG, "Checking notifications for: " + finalCurrentUserId);
                        presenter.getNotificationsByUserId(finalCurrentUserId);
                    }
                    handler.postDelayed(this, INTERVAL_MS);
                }
            };
            handler.post(pollingRunnable);
        } else {
            Log.e(TAG, "User ID is null. Notification checking aborted.");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (handler != null && pollingRunnable != null) {
            handler.removeCallbacks(pollingRunnable);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onGetNotificationsByUserIdSuccess(List<NotificationResponse> notifications) {
        Log.d(TAG, "onGetNotificationsByUserIdSuccess: " + notifications.size());

        if (notifications.size() > lastNotificationCount) {
            int newCount = notifications.size() - lastNotificationCount;
            lastNotificationCount = notifications.size();

            // Cập nhật lại SharedPreferences
            sharedPrefs.edit().putInt("last_notification_count", lastNotificationCount).apply();

            showUserNotification("Bạn có " + newCount + " thông báo mới: " + notifications.get(1).getContent());
        }
    }

    @Override
    public void onGetNotificationsByUserIdFail(String error) {
        Log.e(TAG, "onGetNotificationsByUserIdFail: " + error);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification Check Channel",
                    NotificationManager.IMPORTANCE_HIGH // HIGH để hiển thị tốt hơn
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification createServiceNotification(String contentText) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Risk Alert đang chạy nền")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    private void showUserNotification(String message) {
        // Intent mở MainActivity (hoặc Activity bạn muốn)
        Intent intent = new Intent(this, NotificationsFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Thông báo mới")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent) // ← Gắn PendingIntent vào đây
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), notification);
        }
    }

}
