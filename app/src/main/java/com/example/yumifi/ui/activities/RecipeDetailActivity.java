package com.example.yumifi.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import com.example.yumifi.models.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle, recipeIngredients, recipeDescription;
    private ImageView recipeImage;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Инициализация UI компонентов
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeIngredients = findViewById(R.id.ingredients);
        recipeDescription = findViewById(R.id.description);
        recipeImage = findViewById(R.id.recipeImage);

        // Инициализация базы данных
        databaseHelper = new DatabaseHelper(this);

        // Получаем данные из Intent
        long recipeId = getIntent().getLongExtra("recipeId", -1);

        // Если recipeId передан корректно
        if (recipeId != -1) {
            // Получаем рецепт из базы данных по id
            Recipe recipe = databaseHelper.getRecipeById(recipeId);

            // Если рецепт найден
            if (recipe != null) {
                // Устанавливаем данные в TextView
                recipeTitle.setText(recipe.getTitle());
                recipeIngredients.setText(recipe.getIngredients()); // Убедитесь, что метод getIngredients() существует
                recipeDescription.setText(recipe.getDescription());

                // Попробуем загрузить изображение из базы данных
                byte[] imageBytes = databaseHelper.getRecipeImageById(recipeId);
                if (imageBytes != null) {
                    // Преобразуем byte[] в Bitmap и устанавливаем в ImageView
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    recipeImage.setImageBitmap(bitmap);
                }
            }
        }
    }
}
