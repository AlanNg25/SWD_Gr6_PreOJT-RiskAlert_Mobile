package com.example.preojt_riskalert_mobile.ui.grades;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GradeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GradeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is grade fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}