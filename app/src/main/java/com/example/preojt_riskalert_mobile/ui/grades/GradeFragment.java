package com.example.preojt_riskalert_mobile.ui.grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.preojt_riskalert_mobile.databinding.FragmentGradesBinding;

public class GradeFragment extends Fragment {

    private FragmentGradesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GradeViewModel dashboardViewModel =
                new ViewModelProvider(this).get(GradeViewModel.class);

        binding = FragmentGradesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGrades;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}