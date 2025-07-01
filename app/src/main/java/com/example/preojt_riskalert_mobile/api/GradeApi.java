package com.example.preojt_riskalert_mobile.api;

import com.example.preojt_riskalert_mobile.constants.ConstantApi;
import com.example.preojt_riskalert_mobile.models.response.GradeDetailsResponse;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GradeApi {
    @GET(ConstantApi.GET_ALL_GRADE)
    Call<List<GradeResponse>> getAllGrades(@Path("id") String userId);

    @GET(ConstantApi.GET_GRADE_DETAILS)
    Call<GradeResponse> getGradeDetails(@Path("id") String gradeId);
}
