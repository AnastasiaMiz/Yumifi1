package com.example.yumifi.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import com.example.yumifi.models.Recipe;
import com.example.yumifi.ui.adapters.RecipeAdapter; // Создайте адаптер для отображения рецептов

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchBtn;
    private ListView recipeListView; // Для отображения списка рецептов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchBtn);
        recipeListView = findViewById(R.id.recipeListView); // Инициализация ListView

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipes();
            }
        });
    }

    private void searchRecipes() {
        String query = searchInput.getText().toString();

        if (!query.isEmpty()) {
            // Создание объекта DatabaseHelper для выполнения запроса
            DatabaseHelper dbHelper = new DatabaseHelper(SearchActivity.this);

            // Поиск рецептов
            List<Recipe> recipes = dbHelper.searchRecipes(query);

            if (!recipes.isEmpty()) {
                // Если рецепты найдены, отображаем их в ListView
                RecipeAdapter adapter = new RecipeAdapter(SearchActivity.this, recipes);
                recipeListView.setAdapter(adapter);
            } else {
                // Если рецепты не найдены
                Toast.makeText(SearchActivity.this, "Рецепты не найдены", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SearchActivity.this, "Введите ингредиент или название", Toast.LENGTH_SHORT).show();
        }
    }
}
