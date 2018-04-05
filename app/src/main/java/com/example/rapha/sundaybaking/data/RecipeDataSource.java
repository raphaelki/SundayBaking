package com.example.rapha.sundaybaking.data;

import android.arch.lifecycle.LiveData;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

public interface RecipeDataSource {

    LiveData<List<Recipe>> getRecipes();
}
