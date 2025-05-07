package com.example.yumifi.models;

public class Ingredient {
    private long id;
    private String name;

    // Конструктор
    public Ingredient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
