package com.example.rapha.sundaybaking.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "recipes")
public class Recipe {

    @SerializedName("id")
    private final Integer id;
    @SerializedName("name")
    @PrimaryKey
    @NonNull
    private final String name;
    @SerializedName("ingredients")
    @Ignore
    private final List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Ignore
    private final List<InstructionStep> steps = null;
    @SerializedName("servings")
    private final Integer servings;
    @SerializedName("image")
    private final String image;

    public Recipe(Integer id, @NonNull String name, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<InstructionStep> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
