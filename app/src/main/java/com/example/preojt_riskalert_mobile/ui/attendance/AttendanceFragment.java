package com.example.preojt_riskalert_mobile.ui.attendance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.adapter.AttendanceAdapter;
import com.example.preojt_riskalert_mobile.interfaces.attendance.AttendanceViewImpl;
import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;
import com.example.preojt_riskalert_mobile.presenters.AttendancePresenter;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;

import java.util.ArrayList;
import java.util.List;

public class AttendanceFragment extends Fragment implements AttendanceViewImpl {

    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<AttendanceResponse> dataList = new ArrayList<>();
    private AttendancePresenter attendancePresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AttendanceAdapter(dataList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo Presenter, truyền this làm View
        attendancePresenter = new AttendancePresenter(this, requireContext());

        // TODO: thay bằng userId thật từ session/login
        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
        if (token != null) {
            String userId = JwtUtil.getSubFromToken(token);
            attendancePresenter.getAttendanceByUserId(userId);
        }

    }

    @Override
    public void onAttendanceSuccess(List<AttendanceResponse> attendanceResponse) {
        // Update data và refresh RecyclerView
        dataList.clear();
        dataList.addAll(attendanceResponse);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttendanceFail(String errorMessage) {
        Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
