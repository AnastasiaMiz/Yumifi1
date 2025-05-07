package com.example.yumifi.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Инициализация элементов UI
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        // Инициализация DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Обработчик клика по кнопке "Зарегистрироваться"
        registerButton.setOnClickListener(v -> onRegisterClicked());
    }

    // Метод для обработки регистрации
    public void onRegisterClicked() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Проверка на пустые поля
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        // Добавление пользователя в базу данных
        long userId = dbHelper.addUser(username, password, email);
        if (userId != -1) {
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
            // Вы можете перенаправить пользователя на другую активность
        } else {
            Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
        }
    }
}
