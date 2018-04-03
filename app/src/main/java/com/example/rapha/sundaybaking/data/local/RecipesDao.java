package com.example.rapha.sundaybaking.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    @Insert()
    void insertRecipes(List<Recipe> recipes);

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    LiveData<Recipe> getRecipe(int recipeId);
}
