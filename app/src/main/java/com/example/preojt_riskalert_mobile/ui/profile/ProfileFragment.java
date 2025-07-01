
package com.example.preojt_riskalert_mobile.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.preojt_riskalert_mobile.databinding.FragmentProfileBinding;
import com.example.preojt_riskalert_mobile.interfaces.auth.AuthViewImpl;
import com.example.preojt_riskalert_mobile.interfaces.profile.ProfileViewImpl;
import com.example.preojt_riskalert_mobile.models.response.AuthResponse;
import com.example.preojt_riskalert_mobile.models.response.ProfileResponse;
import com.example.preojt_riskalert_mobile.presenters.AuthPresenter;
import com.example.preojt_riskalert_mobile.presenters.ProfilePresenter;
import com.example.preojt_riskalert_mobile.ui.login.LoginActivity;
import com.example.preojt_riskalert_mobile.utils.GenerateQRCodeUtil;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;

public class ProfileFragment extends Fragment implements ProfileViewImpl, AuthViewImpl {
    private FragmentProfileBinding binding;
    private ProfilePresenter profilePresenter;
    private AuthPresenter authPresenter;
    private ProfileViewModel viewModel;
    private Button btnLogout;
    private static final String TAG = "ProfileFragment";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnLogout = binding.btnLogout;
        btnLogout.setOnClickListener(v -> {
            // Xóa JWT khỏi SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("jwt_token");
            editor.apply();

            // Hiển thị thông báo và chuyển
            authPresenter = new AuthPresenter(this, requireContext());
            authPresenter.logout();


        });

        // Khởi tạo ViewModel và Presenter
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profilePresenter = new ProfilePresenter(this, requireContext());

        // Lắng nghe data từ ViewModel
        viewModel.getProfileData().observe(getViewLifecycleOwner(), this::displayProfile);
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        );

        // Lấy JWT từ SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
        if (token != null) {
            String userId = JwtUtil.getSubFromToken(token);
            Log.d(TAG, "onCreateView: " + token);
            profilePresenter.getProfileById(userId);
        }

        return root;
    }

    private void displayProfile(ProfileResponse profile) {
        binding.tvFullName.setText(profile.getFullName());
        binding.tvStudentInfo.setText("SE180321" + "Status: HD");
        binding.tvEmail.setText(profile.getEmail());

        Bitmap qrCodeBitmap = GenerateQRCodeUtil.generateQrCode("SE180321");
        Glide.with(requireContext())
                .load(qrCodeBitmap)
                .into(binding.imgQrCode);

    }

    @Override
    public void onProfileSuccess(ProfileResponse profileResponse) {
        viewModel.setProfileData(profileResponse);
    }

    @Override
    public void onProfileError(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSignInSuccess(AuthResponse authResponse) {

    }

    @Override
    public void onSignInFailure(String code, String errorMessage) {

    }

    @Override
    public void onSignInByEmailSuccess(AuthResponse authResponse) {

    }

    @Override
    public void onSignInByEmailFailure(String code, String errorMessage) {

    }

    @Override
    public void onLogoutSuccess() {
        Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish(); // Kết thúc Activity hiện tại (thường là MainActivity)
    }

    @Override
    public void onLogoutFailure(String code, String errorMessage) {
        Toast.makeText(requireContext(), "Đăng xuất thất bại" + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
