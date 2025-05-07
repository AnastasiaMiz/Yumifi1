package com.example.yumifi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.yumifi.models.Recipe;  // Модель рецепта
import com.example.yumifi.models.User;    // Модель пользователя

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yumifi.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = "CREATE TABLE User (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "email TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL);";

    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE Recipe (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT NOT NULL, " +
            "description TEXT, " +
            "cooking_time INTEGER, " +
            "difficulty TEXT, " +
            "user_id INTEGER, " +
            "FOREIGN KEY(user_id) REFERENCES User(id));";

    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE Ingredient (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL);";

    private static final String CREATE_TABLE_RECIPE_INGREDIENT = "CREATE TABLE Recipe_Ingredient (" +
            "recipe_id INTEGER, " +
            "ingredient_id INTEGER, " +
            "quantity TEXT, " +
            "PRIMARY KEY(recipe_id, ingredient_id), " +
            "FOREIGN KEY(recipe_id) REFERENCES Recipe(id), " +
            "FOREIGN KEY(ingredient_id) REFERENCES Ingredient(id));";

    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE Comment (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "text TEXT NOT NULL, " +
            "date_created INTEGER, " +
            "recipe_id INTEGER, " +
            "user_id INTEGER, " +
            "FOREIGN KEY(recipe_id) REFERENCES Recipe(id), " +
            "FOREIGN KEY(user_id) REFERENCES User(id));";

    private static final String CREATE_TABLE_RECIPE_IMAGE = "CREATE TABLE Recipe_Image (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "recipe_id INTEGER, " +
            "image BLOB, " +
            "FOREIGN KEY(recipe_id) REFERENCES Recipe(id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENT);
        db.execSQL(CREATE_TABLE_COMMENT);
        db.execSQL(CREATE_TABLE_RECIPE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE Recipe ADD COLUMN image BLOB");
        }
        db.execSQL("DROP TABLE IF EXISTS Recipe_Image");
        db.execSQL("DROP TABLE IF EXISTS Comment");
        db.execSQL("DROP TABLE IF EXISTS Recipe_Ingredient");
        db.execSQL("DROP TABLE IF EXISTS Ingredient");
        db.execSQL("DROP TABLE IF EXISTS Recipe");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    // Метод для добавления нового пользователя
    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);

        return db.insert("User", null, values);
    }

    // Метод для получения пользователя по email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "User",              // Имя таблицы
                null,                // Возвращаем все столбцы
                "email = ?",         // Условие для поиска по email
                new String[]{email}, // Параметры для условия
                null,                // Группировка
                null,                // Сортировка
                null                 // Порядок
        );

        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameColumnIndex = cursor.getColumnIndex("name");
            int emailColumnIndex = cursor.getColumnIndex("email");
            int passwordColumnIndex = cursor.getColumnIndex("password");

            if (idColumnIndex != -1 && nameColumnIndex != -1 && emailColumnIndex != -1 && passwordColumnIndex != -1) {
                User user = new User(
                        cursor.getLong(idColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(emailColumnIndex),
                        cursor.getString(passwordColumnIndex)
                );
                cursor.close();
                return user;
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }

    // Метод для добавления нового рецепта
    public long addRecipe(String title, String description, int cookingTime, String difficulty, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("cooking_time", cookingTime);
        values.put("difficulty", difficulty);
        values.put("user_id", userId);

        return db.insert("Recipe", null, values);
    }

    public List<Recipe> getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Recipe> recipes = new ArrayList<>();

        // Запрос для получения всех рецептов
        Cursor cursor = db.query("Recipe", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                // Индексы столбцов
                int idIndex = cursor.getColumnIndex("id");
                int titleIndex = cursor.getColumnIndex("title");
                int descriptionIndex = cursor.getColumnIndex("description");
                int cookingTimeIndex = cursor.getColumnIndex("cooking_time");
                int difficultyIndex = cursor.getColumnIndex("difficulty");
                int userIdIndex = cursor.getColumnIndex("user_id");

                // Проверка наличия всех необходимых столбцов
                if (idIndex == -1 || titleIndex == -1 || descriptionIndex == -1 ||
                        cookingTimeIndex == -1 || difficultyIndex == -1 || userIdIndex == -1) {
                    Log.e("DatabaseHelper", "One or more columns are missing in the query result.");
                    return recipes; // Возвращаем пустой список, если один из столбцов не найден
                }

                // Итерация по всем рецептам в результатах запроса
                while (cursor.moveToNext()) {
                    // Получаем данные рецепта
                    long recipeId = cursor.getLong(idIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    int cookingTime = cursor.getInt(cookingTimeIndex);
                    String difficulty = cursor.getString(difficultyIndex);
                    long userId = cursor.getLong(userIdIndex);

                    // Получаем ингредиенты для рецепта (если нужно)
                    String ingredients = getIngredientsByRecipeId(recipeId);

                    // Создаем объект рецепта
                    Recipe recipe = new Recipe(
                            recipeId, title, description, cookingTime, difficulty, userId, ingredients
                    );

                    // Добавляем рецепт в список
                    recipes.add(recipe);
                }
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error while fetching recipes", e);
            } finally {
                cursor.close();
            }
        }

        return recipes;
    }


    // Метод для добавления изображения рецепта
    public long addRecipeImage(long recipeId, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("image", imageBytes);

        return db.insert("Recipe_Image", null, values);
    }

    // Метод для получения рецепта по ID, включая ингредиенты
    public Recipe getRecipeById(long recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Запрос для получения данных рецепта
        Cursor cursor = db.query(
                "Recipe",                // Имя таблицы
                null,                     // Все столбцы
                "id = ?",                // Условие
                new String[]{String.valueOf(recipeId)}, // Параметры
                null,                     // Группировка
                null,                     // Сортировка
                null                      // Порядок
        );

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int cookingTimeIndex = cursor.getColumnIndex("cooking_time");
            int difficultyIndex = cursor.getColumnIndex("difficulty");
            int userIdIndex = cursor.getColumnIndex("user_id");

            if (idIndex != -1 && titleIndex != -1 && descriptionIndex != -1 &&
                    cookingTimeIndex != -1 && difficultyIndex != -1 && userIdIndex != -1) {

                String ingredients = getIngredientsByRecipeId(recipeId); // Получаем ингредиенты для рецепта

                Recipe recipe = new Recipe(
                        cursor.getLong(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getInt(cookingTimeIndex),
                        cursor.getString(difficultyIndex),
                        cursor.getLong(userIdIndex),
                        ingredients // Передаем ингредиенты в объект рецепта
                );
                cursor.close();
                return recipe;
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }

    // Метод для получения ингредиентов по id рецепта
    private String getIngredientsByRecipeId(long recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Recipe_Ingredient",              // Имя таблицы
                new String[]{"ingredient_id"},    // Возвращаем только id ингредиента
                "recipe_id = ?",                  // Условие по id рецепта
                new String[]{String.valueOf(recipeId)}, // Параметры
                null,                              // Группировка
                null,                              // Сортировка
                null                               // Порядок
        );

        StringBuilder ingredients = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Индекс столбца "ingredient_id"
                int ingredientIdIndex = cursor.getColumnIndex("ingredient_id");
                if (ingredientIdIndex == -1) {
                    Log.e("DatabaseHelper", "Column 'ingredient_id' not found");
                    return ingredients.toString();
                }

                do {
                    // Получаем id ингредиента
                    long ingredientId = cursor.getLong(ingredientIdIndex);

                    // Теперь извлекаем название ингредиента из таблицы Ingredient
                    Cursor ingredientCursor = db.query(
                            "Ingredient",                  // Имя таблицы
                            new String[]{"name"},           // Возвращаем имя ингредиента
                            "id = ?",                      // Условие по id ингредиента
                            new String[]{String.valueOf(ingredientId)}, // Параметры
                            null,                           // Группировка
                            null,                           // Сортировка
                            null                            // Порядок
                    );

                    if (ingredientCursor != null && ingredientCursor.moveToFirst()) {
                        int nameIndex = ingredientCursor.getColumnIndex("name");
                        if (nameIndex != -1) {
                            String ingredientName = ingredientCursor.getString(nameIndex);
                            ingredients.append(ingredientName).append(", "); // Добавляем ингредиент в строку
                        } else {
                            Log.e("DatabaseHelper", "Column 'name' not found in Ingredient table");
                        }
                        ingredientCursor.close();
                    }

                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error while fetching ingredients", e);
            } finally {
                cursor.close();
            }
        }

        // Возвращаем строку с ингредиентами (удаляем последнюю запятую)
        if (ingredients.length() > 0) {
            ingredients.setLength(ingredients.length() - 2);
        }

        return ingredients.toString();
    }

    // Метод для получения изображения рецепта по id
    public byte[] getRecipeImageById(long recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Recipe_Image",                // Имя таблицы
                new String[]{"image"},         // Столбец для извлечения
                "recipe_id = ?",              // Условие
                new String[]{String.valueOf(recipeId)}, // Параметры
                null,                          // Группировка
                null,                          // Сортировка
                null                           // Порядок
        );

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int imageIndex = cursor.getColumnIndex("image");
                if (imageIndex != -1) {
                    byte[] imageBytes = cursor.getBlob(imageIndex);
                    cursor.close();
                    return imageBytes; // Возвращаем изображение в виде byte[]
                } else {
                    Log.e("DatabaseHelper", "Column 'image' not found in Recipe_Image table");
                }
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error while fetching recipe image", e);
            } finally {
                cursor.close();
            }
        }

        return null; // Если изображение не найдено
    }

// Внутри класса DatabaseHelper

    public List<Recipe> searchRecipes(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Recipe> recipes = new ArrayList<>();

        // Поиск рецептов по названию или описанию
        String selection = "title LIKE ? OR description LIKE ?";
        String[] selectionArgs = { "%" + query + "%", "%" + query + "%" };

        Cursor cursor = db.query(
                "Recipe",           // Таблица
                null,               // Все столбцы
                selection,          // Условие
                selectionArgs,      // Аргументы
                null,               // Группировка
                null,               // Сортировка
                null                // Порядок
        );

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int titleIndex = cursor.getColumnIndex("title");
                int descriptionIndex = cursor.getColumnIndex("description");
                int cookingTimeIndex = cursor.getColumnIndex("cooking_time");
                int difficultyIndex = cursor.getColumnIndex("difficulty");
                int userIdIndex = cursor.getColumnIndex("user_id");

                while (cursor.moveToNext()) {
                    long recipeId = cursor.getLong(idIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    int cookingTime = cursor.getInt(cookingTimeIndex);
                    String difficulty = cursor.getString(difficultyIndex);
                    long userId = cursor.getLong(userIdIndex);

                    String ingredients = getIngredientsByRecipeId(recipeId);

                    Recipe recipe = new Recipe(
                            recipeId, title, description, cookingTime, difficulty, userId, ingredients
                    );

                    recipes.add(recipe);
                }
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error while searching recipes", e);
            } finally {
                cursor.close();
            }
        }

        return recipes;
    }
    // Получить всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("User", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                users.add(new User(id, name, email, password));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return users;
    }

}
