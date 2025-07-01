package com.example.preojt_riskalert_mobile.ui.attendance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.preojt_riskalert_mobile.interfaces.attendance.AttendanceViewImpl;
import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;
import com.example.preojt_riskalert_mobile.presenters.AttendancePresenter;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel implements AttendanceViewImpl {

    private AttendancePresenter presenter;
    private MutableLiveData<List<AttendanceResponse>> attendanceList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        presenter = new AttendancePresenter(this, application.getApplicationContext());
    }

    public void loadAttendance(String userId) {
        presenter.getAttendanceByUserId(userId);
    }

    public MutableLiveData<List<AttendanceResponse>> getAttendanceList() {
        return attendanceList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    @Override
    public void onAttendanceSuccess(List<AttendanceResponse> data) {
        attendanceList.postValue(data);
    }

    @Override
    public void onAttendanceFail(String message) {
        error.postValue(message);
    }
}
