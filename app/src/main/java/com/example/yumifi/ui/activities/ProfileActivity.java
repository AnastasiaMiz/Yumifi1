package com.example.yumifi.ui.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper; // Подключение к базе данных
import com.example.yumifi.models.User; // Модель пользователя

public class ProfileActivity extends AppCompatActivity {

    private TextView userName, userEmail;
    private DatabaseHelper databaseHelper; // Экземпляр базы данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Инициализация компонентов UI
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        // Инициализация базы данных
        databaseHelper = new DatabaseHelper(this);

        // Получаем email текущего пользователя (это может быть передано через Intent или SharedPreferences)
        String userEmailFromIntent = "email@domain.com"; // В реальном приложении это должно быть динамически

        // Получаем информацию о пользователе из базы данных
        User user = databaseHelper.getUserByEmail(userEmailFromIntent); // Параметр — это email пользователя

        // Если пользователь найден, заполняем данные в UI
        if (user != null) {
            userName.setText(user.getName());
            userEmail.setText(user.getEmail());
        } else {
            // Если пользователь не найден в базе данных, выводим дефолтные значения или ошибку
            userName.setText("Имя пользователя не найдено");
            userEmail.setText("email@domain.com");
        }
    }
}
