package com.example.rapha.sundaybaking.data.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("quantity")
    public Integer quantity;
    @SerializedName("measure")
    public String measure;
    @SerializedName("ingredient")
    public String ingredient;

    public Integer getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
