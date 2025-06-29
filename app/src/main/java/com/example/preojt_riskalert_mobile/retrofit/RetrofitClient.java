package com.example.preojt_riskalert_mobile.retrofit;

import android.content.Context;

import com.example.preojt_riskalert_mobile.api.AuthApi;
import com.example.preojt_riskalert_mobile.api.ProfileApi;
import com.example.preojt_riskalert_mobile.constants.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(Context context) {
        if (instance == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context)) // Thêm interceptor ở đây
                    .build();
            instance = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL) // Replace with your base URL
                    .addConverterFactory(GsonConverterFactory.create())
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
}
