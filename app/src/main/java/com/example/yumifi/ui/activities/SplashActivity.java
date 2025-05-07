package com.example.yumifi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import com.example.yumifi.models.User;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2 секунды

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Запускаем проверку после SPLASH_DISPLAY_LENGTH миллисекунд
        new Handler(Looper.getMainLooper()).postDelayed(this::checkUserAndRedirect, SPLASH_DISPLAY_LENGTH);
    }

    private void checkUserAndRedirect() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Простой способ: проверим, есть ли хоть один пользователь
        List<User> users = dbHelper.getAllUsers();

        if (users != null && !users.isEmpty()) {
            // Пользователь найден — переходим в MainActivity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Пользователей нет — переходим на экран входа
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish(); // Закрываем SplashActivity
    }
}
