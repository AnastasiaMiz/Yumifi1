package com.example.yumifi.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import com.example.yumifi.ui.fragments.HomeFragment;
import com.example.yumifi.ui.fragments.SearchFragment;
import com.example.yumifi.ui.fragments.AddRecipeFragment;
import com.example.yumifi.ui.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper databaseHelper; // Создаем переменную для работы с базой данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем базу данных
        databaseHelper = new DatabaseHelper(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Устанавливаем слушатель для навигации
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment(); // Главная страница
            } else if (item.getItemId() == R.id.nav_search) {
                selectedFragment = new SearchFragment(); // Поиск рецептов
            } else if (item.getItemId() == R.id.nav_add) {
                selectedFragment = new AddRecipeFragment(); // Добавление рецепта
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment(); // Профиль пользователя
            }

            // Загружаем соответствующий фрагмент
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;


        });

        // Загружаем фрагмент по умолчанию (Главная страница)
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Закрываем базу данных при уничтожении активности
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
