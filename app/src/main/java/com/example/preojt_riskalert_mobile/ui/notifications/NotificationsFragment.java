package com.example.preojt_riskalert_mobile.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.preojt_riskalert_mobile.adapter.NotificationAdapter;
import com.example.preojt_riskalert_mobile.databinding.FragmentNotificationsBinding;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        viewModel.init(requireContext());

        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getNotifications().observe(getViewLifecycleOwner(), list -> {
            NotificationAdapter adapter = new NotificationAdapter(getContext(), list);
            binding.rvNotifications.setAdapter(adapter);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), msg ->
                Toast.makeText(getContext(), "Error: " + msg, Toast.LENGTH_SHORT).show());

        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
        if (token != null) {
            String userId = JwtUtil.getSubFromToken(token);
            viewModel.loadNotifications(userId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
