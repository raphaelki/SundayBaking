package com.example.rapha.sundaybaking.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("ingredients")
    public List<Ingredient> ingredients = null;
    @SerializedName("steps")
    public List<InstructionStep> steps = null;
    @SerializedName("servings")
    public Integer servings;
    @SerializedName("image")
    public String image;

    public Integer getId() {
        return id;
    }

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
