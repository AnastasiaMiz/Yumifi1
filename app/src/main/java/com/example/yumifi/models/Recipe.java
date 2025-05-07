package com.example.yumifi.models;

public class Recipe {
    private long id;
    private String title;
    private String description;
    private int cookingTime;
    private String difficulty;
    private long userId;
    private String ingredients; // Добавляем поле для ингредиентов

    public Recipe(long id, String title, String description, int cookingTime, String difficulty, long userId, String ingredients) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
        this.userId = userId;
        this.ingredients = ingredients;  // Инициализация ингредиентов
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIngredients() {
        return ingredients;  // Геттер для ингредиентов
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;  // Сеттер для ингредиентов
    }
}
