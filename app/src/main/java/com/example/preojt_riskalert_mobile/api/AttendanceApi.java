package com.example.preojt_riskalert_mobile.api;

import com.example.preojt_riskalert_mobile.constants.ConstantApi;
import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AttendanceApi {
    @GET(ConstantApi.GET_ATTENDANCE)
    Call<List<AttendanceResponse>> getAttendance(@Path("id") String userId);
}
