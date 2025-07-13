package com.example.preojt_riskalert_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.preojt_riskalert_mobile.databinding.ActivityMainBinding;
import com.example.preojt_riskalert_mobile.services.NotificationCheckService;
import com.example.preojt_riskalert_mobile.utils.JwtUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String currentUserId = null;
        SharedPreferences prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
        if (token != null) {
            currentUserId = JwtUtil.getSubFromToken(token);
        }

        Intent intent = new Intent(this, NotificationCheckService.class);
        intent.putExtra("userId", currentUserId); // truyền ID người dùng thật
        ContextCompat.startForegroundService(this, intent);


        // Gắn toolbar làm ActionBar
        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_attendance, R.id.navigation_grades,
                R.id.navigation_notifications, R.id.navigation_profile
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            int currentDestId = navController.getCurrentDestination().getId();

            if (itemId == R.id.navigation_grades) {
                // Nếu đang không ở fragment_grade thì pop về fragment_grade
                if (currentDestId != R.id.navigation_grades) {
                    navController.popBackStack(R.id.navigation_grades, false);
                    navController.navigate(R.id.navigation_grades);
                }
                return true;
            } else if (itemId == R.id.navigation_attendance) {
                navController.navigate(R.id.navigation_attendance);
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                navController.navigate(R.id.navigation_notifications);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                navController.navigate(R.id.navigation_profile);
                return true;
            }

            return false;
        });

    }


}
