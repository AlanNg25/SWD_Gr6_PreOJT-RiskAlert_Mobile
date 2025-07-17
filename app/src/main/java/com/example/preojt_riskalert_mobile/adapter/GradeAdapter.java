package com.example.preojt_riskalert_mobile.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    private List<GradeResponse> grades;
    private final OnGradeClickListener listener;

    public interface OnGradeClickListener {
        void onClick(String gradeId);
    }

    public GradeAdapter(List<GradeResponse> grades, OnGradeClickListener listener) {
        this.grades = grades;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        GradeResponse grade = grades.get(position);
        holder.txtSubjectTitle.setText(grade.getCourse().getCourseCode());
        holder.txtAverage.setText("Average: " + grade.getScoreAverage());
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(grade.getGradeID()); // grade.getGradeID() phải là String
        });

        if (grade.getScoreAverage() >= 5.0) {
            holder.txtStatus.setText("Passed");
            holder.txtStatus.setTextColor(Color.parseColor("#2E7D32")); // Green
            holder.txtStatus.setBackgroundResource(R.drawable.bg_passed_circle);
        } else {
            holder.txtStatus.setText("Not Passed");
            holder.txtStatus.setTextColor(Color.RED);
            holder.txtStatus.setBackgroundResource(R.drawable.bg_failed_circle);
        }
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubjectTitle, txtAverage, txtStatus;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubjectTitle = itemView.findViewById(R.id.txtSubject);
            txtAverage = itemView.findViewById(R.id.txtAverage);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}

