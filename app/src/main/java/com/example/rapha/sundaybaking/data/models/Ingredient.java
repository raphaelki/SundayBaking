package com.example.rapha.sundaybaking.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "name",
                childColumns = "recipe_name",
                onDelete = CASCADE),
        tableName = "ingredients")
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("quantity")
    private Double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    public Ingredient(int id, Double quantity, String measure, String ingredient, String recipeName) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
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

//        public void setRecipeName(String recipeName) {
//        this.recipeName = recipeName;
//    }


//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setQuantity(Double quantity) {
//        this.quantity = quantity;
//    }
//
//    public void setMeasure(String measure) {
//        this.measure = measure;
//    }
//
//    public void setIngredient(String ingredient) {
//        this.ingredient = ingredient;
//    }
}
