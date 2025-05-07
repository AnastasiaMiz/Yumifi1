package com.example.yumifi.models;

public class Comment {
    private long id;
    private String text;
    private long recipeId;
    private long userId;
    private long dateCreated;

    // Конструктор
    public Comment(long id, String text, long recipeId, long userId, long dateCreated) {
        this.id = id;
        this.text = text;
        this.recipeId = recipeId;
        this.userId = userId;
        this.dateCreated = dateCreated;
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
