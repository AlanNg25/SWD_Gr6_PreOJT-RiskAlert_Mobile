package com.example.preojt_riskalert_mobile.interfaces.grade;

import com.example.preojt_riskalert_mobile.models.response.GradeDetailsResponse;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;

import java.util.List;

public interface GradeViewImpl {
    void onGetGradeByUserIdSuccess(List<GradeResponse> gradeResponseList);

    void onGetGradeByUserIdFail(String errorMessage);
}
