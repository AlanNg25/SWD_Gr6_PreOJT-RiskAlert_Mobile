package com.example.preojt_riskalert_mobile.ui.grades;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.adapter.GradeAdapter;
import com.example.preojt_riskalert_mobile.databinding.FragmentGradesBinding;
import com.example.preojt_riskalert_mobile.models.response.GradeResponse;
import com.example.preojt_riskalert_mobile.ui.gradeDetails.GradeDetailFragment;

import java.util.List;

public class GradeFragment extends Fragment {
    private static final String TAG = "GradeFragment";
    private FragmentGradesBinding binding;
    private GradeViewModel gradeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGradesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gradeViewModel = new ViewModelProvider(this).get(GradeViewModel.class);

        // Setup RecyclerView
        binding.recyclerViewGrades.setLayoutManager(new LinearLayoutManager(getContext()));

        // Observe data
        gradeViewModel.getGradeList().observe(getViewLifecycleOwner(), this::setupRecyclerView);
        gradeViewModel.getError().observe(getViewLifecycleOwner(), err ->
                Toast.makeText(getContext(), "Error: " + err, Toast.LENGTH_SHORT).show());

        // Trigger load (userId phải truyền từ SharedPreferences / arguments...)
        gradeViewModel.loadGrades("afa69a5a-2317-4d4e-84a0-da3d22ccfffd");

        return root;
    }

    private void setupRecyclerView(List<GradeResponse> grades) {
        GradeAdapter adapter = new GradeAdapter(grades, gradeId -> {
            NavController navController = NavHostFragment.findNavController(this);
            Bundle bundle = new Bundle();
            bundle.putString("gradeId", gradeId);
            navController.navigate(R.id.navigation_gradeDetail, bundle);
        });

        binding.recyclerViewGrades.setAdapter(adapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
