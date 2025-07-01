package com.example.preojt_riskalert_mobile.ui.grades;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.preojt_riskalert_mobile.interfaces.grade.GradeViewImpl;
import com.example.preojt_riskalert_mobile.models.response.GradeDetailsResponse;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;
import com.example.preojt_riskalert_mobile.presenters.GradePresenter;

import java.util.List;

public class GradeViewModel extends AndroidViewModel implements GradeViewImpl {

    private final MutableLiveData<List<GradeResponse>> gradeList = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final GradePresenter presenter;

    public GradeViewModel(@NonNull Application application) {
        super(application);
        presenter = new GradePresenter(this,null, application.getApplicationContext());
    }

    public void loadGrades(String userId) {
        presenter.getGradeByUserId(userId);
    }

    public MutableLiveData<List<GradeResponse>> getGradeList() {
        return gradeList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    @Override
    public void onGetGradeByUserIdSuccess(List<GradeResponse> gradeResponseList) {
        gradeList.postValue(gradeResponseList);
    }

    @Override
    public void onGetGradeByUserIdFail(String errorMessage) {
        error.postValue(errorMessage);
    }

}
