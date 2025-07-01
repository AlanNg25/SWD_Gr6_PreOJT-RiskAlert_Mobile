package com.example.preojt_riskalert_mobile.interfaces.attendance;

import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;

import java.util.List;

public interface AttendanceViewImpl {
    void onAttendanceSuccess(List<AttendanceResponse> attendanceResponse);

    void onAttendanceFail(String errorMessage);
}
