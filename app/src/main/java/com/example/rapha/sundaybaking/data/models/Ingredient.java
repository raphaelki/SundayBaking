package com.example.rapha.sundaybaking.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "name",
                childColumns = "recipe_name",
                onDelete = CASCADE),
        indices = {@Index(value = "recipe_name")},
        tableName = "ingredients")
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private final int id;
    @SerializedName("quantity")
    private final Double quantity;
    @SerializedName("measure")
    private final String measure;
    @SerializedName("ingredient")
    private final String ingredient;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    public Ingredient(int id, Double quantity, String measure, String ingredient, String recipeName) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeName = recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
