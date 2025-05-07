package com.example.yumifi.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализируем компоненты UI
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Инициализация DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Устанавливаем обработчик клика для кнопки "Зарегистрироваться"
        buttonRegister.setOnClickListener(v -> registerUser());
    }

    // Метод для регистрации нового пользователя
    private void registerUser() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Проверка на пустые поля
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка на уникальность email
        if (isEmailExists(email)) {
            Toast.makeText(this, "Этот email уже зарегистрирован.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Добавляем пользователя в базу данных
        long userId = databaseHelper.addUser(name, email, password);
        if (userId != -1) {
            // Если пользователь успешно добавлен
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
        } else {
            // Если произошла ошибка при добавлении
            Toast.makeText(this, "Ошибка регистрации.", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для проверки, существует ли уже пользователь с таким email
    private boolean isEmailExists(String email) {
        return databaseHelper.getUserByEmail(email) != null;
    }
}
