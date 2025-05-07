package com.example.yumifi.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText titleInput, ingredientsInput, descriptionInput;
    private Button selectImageBtn, saveRecipeBtn;
    private ImageView selectedImage;
    private Uri imageUri;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Инициализация UI-компонентов
        titleInput = findViewById(R.id.titleInput);
        ingredientsInput = findViewById(R.id.ingredientsInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        selectImageBtn = findViewById(R.id.selectImageBtn);
        saveRecipeBtn = findViewById(R.id.saveRecipeBtn);
        selectedImage = findViewById(R.id.selectedImage);

        // Инициализация базы данных
        databaseHelper = new DatabaseHelper(this);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        saveRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void saveRecipe() {
        String title = titleInput.getText().toString();
        String ingredients = ingredientsInput.getText().toString();
        String description = descriptionInput.getText().toString();

        if (title.isEmpty() || ingredients.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {
            long recipeId = saveRecipeToDatabase(title, ingredients, description);
            if (recipeId != -1 && imageUri != null) {
                saveImageToDatabase(recipeId);
            }
            Toast.makeText(this, "Рецепт сохранён", Toast.LENGTH_SHORT).show();
            finish();  // Закрытие Activity после сохранения
        }
    }

    private long saveRecipeToDatabase(String title, String ingredients, String description) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("ingredients", ingredients);
        values.put("description", description);

        // Сохранение рецепта в базу данных
        return db.insert("Recipe", null, values);
    }

    private void saveImageToDatabase(long recipeId) {
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("recipe_id", recipeId);
            values.put("image", imageUriToByteArray(imageUri));

            // Сохранение изображения в таблицу Recipe_Image
            db.insert("Recipe_Image", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении изображения", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] imageUriToByteArray(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            inputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            selectedImage.setImageURI(imageUri);
        }
    }
}
