package com.example.preojt_riskalert_mobile.ui.gradeDetails;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.preojt_riskalert_mobile.interfaces.grade.GradeDetailViewImpl;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;
import com.example.preojt_riskalert_mobile.presenters.GradePresenter;

public class GradeDetailViewModel extends AndroidViewModel implements GradeDetailViewImpl {

    private final MutableLiveData<GradeResponse> gradeData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final GradePresenter presenter;

    public GradeDetailViewModel(@NonNull Application application) {
        super(application);
        presenter = new GradePresenter(null,this, application.getApplicationContext());
    }

    public void loadGradeDetails(String gradeId) {
        presenter.getGradeDetailsByGradeId(gradeId);
    }

    public MutableLiveData<GradeResponse> getGradeData() {
        return gradeData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    @Override
    public void onGetGradeDetailsByGradeIdSuccess(GradeResponse gradeResponse) {
        gradeData.postValue(gradeResponse);
    }

    @Override
    public void onGetGradeDetailsByGradeIdFail(String errorMessage) {
        error.postValue(errorMessage);
    }
}
