package com.example.preojt_riskalert_mobile.retrofit;

import android.content.Context;

import com.example.preojt_riskalert_mobile.api.AttendanceApi;
import com.example.preojt_riskalert_mobile.api.AuthApi;
import com.example.preojt_riskalert_mobile.api.GradeApi;
import com.example.preojt_riskalert_mobile.api.NotificationApi;
import com.example.preojt_riskalert_mobile.api.ProfileApi;
import com.example.preojt_riskalert_mobile.constants.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(Context context) {
        if (instance == null) {
            // Define a custom DateTimeFormatter for LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class,
                            (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                                    LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .create();


            // Create OkHttpClient with an interceptor for authentication
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context)) // Thêm interceptor ở đây
                    .build();

            instance = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL) // Replace with your base URL
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }

    public static AuthApi getAuthApi(Context context) {
        return getInstance(context).create(AuthApi.class);
    }

    public static ProfileApi getProfileApi(Context context) {
        return getInstance(context).create(ProfileApi.class);
    }

    public  static AttendanceApi getAttendanceApi(Context context) {
        return getInstance(context).create(AttendanceApi.class);
    }

    public  static GradeApi getGradeApi(Context context) {
        return getInstance(context).create(GradeApi.class);
    }

    public static NotificationApi getNotificationApi(Context context) {
        return getInstance(context).create(NotificationApi.class);
    }
}
