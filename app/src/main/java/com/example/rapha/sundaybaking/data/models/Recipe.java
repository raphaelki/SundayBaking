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
    private Integer id;
    @SerializedName("name")
    @PrimaryKey
    @NonNull
    private String name;
    @SerializedName("ingredients")
    @Ignore
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Ignore
    private List<InstructionStep> steps = null;
    @SerializedName("servings")
    private Integer servings;
    @SerializedName("image")
    private String image;

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

//    public void setId(Integer id) {
//        this.id = id;
//    }

//    public void setName(@NonNull String name) {
//        this.name = name;
//    }

//    public void setIngredients(List<Ingredient> ingredients) {
//        this.ingredients = ingredients;
//    }

//    public void setSteps(List<InstructionStep> steps) {
//        this.steps = steps;
//    }

//    public void setServings(Integer servings) {
//        this.servings = servings;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }
}
