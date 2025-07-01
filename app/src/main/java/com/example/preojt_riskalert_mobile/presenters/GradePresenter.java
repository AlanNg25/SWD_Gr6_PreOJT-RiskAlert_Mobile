package com.example.preojt_riskalert_mobile.presenters;

import android.content.Context;

import com.example.preojt_riskalert_mobile.api.GradeApi;
import com.example.preojt_riskalert_mobile.interfaces.grade.GradeDetailPresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.grade.GradeDetailViewImpl;
import com.example.preojt_riskalert_mobile.interfaces.grade.GradePresenterImpl;
import com.example.preojt_riskalert_mobile.interfaces.grade.GradeViewImpl;
import com.example.preojt_riskalert_mobile.models.response.GradeDetailsResponse;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;
import com.example.preojt_riskalert_mobile.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradePresenter implements GradePresenterImpl, GradeDetailPresenterImpl {

    private static final String TAG = "GradePresenter";
    private GradeApi mGradeApi;
    private GradeViewImpl mGradeView;

    private GradeDetailViewImpl mGradeDetailView;

    public GradePresenter(GradeViewImpl gradeView,GradeDetailViewImpl gradeDetailView , Context context) {
        this.mGradeView = gradeView;
        this.mGradeDetailView = gradeDetailView;
        if(mGradeApi == null) {
            mGradeApi = RetrofitClient.getGradeApi(context);
        }
    }

    @Override
    public void getGradeByUserId(String userId) {
        mGradeApi.getAllGrades(userId).enqueue(new Callback<List<GradeResponse>>() {
            @Override
            public void onResponse(Call<List<GradeResponse>> call, Response<List<GradeResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    mGradeView.onGetGradeByUserIdSuccess(response.body());
                } else {
                    mGradeView.onGetGradeByUserIdFail("Failed to retrieve: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GradeResponse>> call, Throwable t) {
                mGradeView.onGetGradeByUserIdFail("Failed to retrieve grades: " + t.getMessage());
            }
        });
    }


    @Override
    public void getGradeDetailsByGradeId(String gradeId) {
        mGradeApi.getGradeDetails(gradeId).enqueue(new Callback<GradeResponse>() {
            @Override
            public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GradeResponse gradeResponse = response.body();
                    if (mGradeDetailView != null) {
                        mGradeDetailView.onGetGradeDetailsByGradeIdSuccess(gradeResponse);
                    }
                } else {
                    if (mGradeDetailView != null) {
                        mGradeDetailView.onGetGradeDetailsByGradeIdFail("Failed to retrieve: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<GradeResponse> call, Throwable t) {
                mGradeDetailView.onGetGradeDetailsByGradeIdFail("Failed to retrieve: " + t.getMessage());
            }
        });
    }
}
