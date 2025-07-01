package com.example.preojt_riskalert_mobile.ui.gradeDetails;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.databinding.FragmentGradeDetailBinding;
import com.example.preojt_riskalert_mobile.models.response.GradeDetailsResponse;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class GradeDetailFragment extends Fragment {

    private FragmentGradeDetailBinding binding;
    private GradeDetailViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGradeDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(GradeDetailViewModel.class);

        // Nhận gradeId từ arguments
        String gradeId = getArguments() != null ? getArguments().getString("gradeId") : null;
        if (gradeId != null) {
            viewModel.loadGradeDetails(gradeId); // Gọi presenter
        }

        // Quan sát dữ liệu và lỗi
        viewModel.getGradeData().observe(getViewLifecycleOwner(), this::bindData);
        viewModel.getError().observe(getViewLifecycleOwner(),
                err -> Toast.makeText(getContext(), "Error: " + err, Toast.LENGTH_SHORT).show());
    }

    private void bindData(GradeResponse grade) {
        if (grade != null) {
            binding.txtSubjectCode.setText(grade.getCourse().getCourseCode());
            binding.txtAverage.setText("Average: " + grade.getScoreAverage());
            if (grade.getScoreAverage()/10 >= 5.0) {
                binding.txtStatus.setText("Passed");
                binding.txtStatus.setTextColor(Color.parseColor("#2E7D32")); // Green
                binding.txtStatus.setBackgroundResource(R.drawable.bg_passed_circle);
            } else {
                binding.txtStatus.setText("Not Passed");
                binding.txtStatus.setTextColor(Color.RED);
                binding.txtStatus.setBackgroundResource(R.drawable.bg_failed_circle);
            }
            // Có thể thêm các binding khác tại đây
            renderGradeDetails(grade.getGradeDetails());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Luôn chọn lại tab "Grades" khi đang ở GradeDetail
        BottomNavigationView nav = requireActivity().findViewById(R.id.nav_view);
        nav.getMenu().findItem(R.id.navigation_grades).setChecked(true);
    }


    private void renderGradeDetails(List<GradeDetailsResponse> details) {
        TableLayout table = binding.getRoot().findViewById(R.id.tableGradeDetails);

        for (GradeDetailsResponse item : details) {
            TableRow row = new TableRow(getContext());

            TextView category = new TextView(getContext());
            category.setText(item.getGradeType());
            category.setPadding(8, 8, 8, 8);

            TextView weight = new TextView(getContext());
            weight.setText(String.valueOf(item.getScoreWeight()));
            weight.setPadding(8, 8, 8, 8);

            TextView value = new TextView(getContext());
            value.setText(String.valueOf(item.getScore()));
            value.setPadding(8, 8, 8, 8);

            row.addView(category);
            row.addView(weight);
            row.addView(value);

            table.addView(row);
        }
    }

}
