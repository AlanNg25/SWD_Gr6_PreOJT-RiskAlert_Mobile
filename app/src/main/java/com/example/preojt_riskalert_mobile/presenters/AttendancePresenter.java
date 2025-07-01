package com.example.preojt_riskalert_mobile.presenters;

import android.content.Context;
import android.util.Log;

import com.example.preojt_riskalert_mobile.api.AttendanceApi;
import com.example.preojt_riskalert_mobile.api.ProfileApi;
import com.example.preojt_riskalert_mobile.interfaces.attendance.AttendancePresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.attendance.AttendanceViewImpl;
import com.example.preojt_riskalert_mobile.interfaces.profile.ProfileViewImpl;
import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendancePresenter implements AttendancePresenterImpl {

    private static final String TAG = "AttendancePresenter";
    private AttendanceViewImpl mAttendanceView;
    private AttendanceApi mAttendanceApi;

    public AttendancePresenter(AttendanceViewImpl attendanceView, Context context) {
        this.mAttendanceView = attendanceView;
        if (mAttendanceApi == null) {
            mAttendanceApi = RetrofitClient.getAttendanceApi(context);
        }

    }
    @Override
    public void getAttendanceByUserId(String userId) {
        mAttendanceApi.getAttendance(userId).enqueue(new Callback<List<AttendanceResponse>>() {
            @Override
            public void onResponse(Call<List<AttendanceResponse>> call, Response<List<AttendanceResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAttendanceView.onAttendanceSuccess(response.body());
                    Log.d(TAG, "Attendance data retrieved successfully: " + response.toString());

                } else {
                    mAttendanceView.onAttendanceFail("Failed to retrieve attendance data" );
                }
            }

            @Override
            public void onFailure(Call<List<AttendanceResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                mAttendanceView.onAttendanceFail("Failed to retrieve attendance data: " + t.getMessage());
            }
        });
    }
}
