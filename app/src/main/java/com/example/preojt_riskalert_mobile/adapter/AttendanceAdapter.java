package com.example.preojt_riskalert_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.customviews.CustomProgressView;
import com.example.preojt_riskalert_mobile.models.response.AttendanceResponse;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<AttendanceResponse> items;

    public AttendanceAdapter(List<AttendanceResponse> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        AttendanceResponse item = items.get(position);
        holder.txtSubject.setText("SubjectName: " + item.getCourse().getCourseCode());
        holder.txtAttendance.setText("Session Number: " + item.getAttendNumber() + "/" + item.getSessionNumber());
        holder.progressView.setPercentage((item.getAttendNumber() * 100) / item.getSessionNumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubject, txtAttendance;
        CustomProgressView progressView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtAttendance = itemView.findViewById(R.id.txtAttendance);
            progressView = itemView.findViewById(R.id.progressView);
        }
    }
}

