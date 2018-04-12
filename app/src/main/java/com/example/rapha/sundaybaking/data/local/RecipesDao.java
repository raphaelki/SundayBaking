package com.example.rapha.sundaybaking.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM recipes WHERE name = :recipeName")
    LiveData<Recipe> getRecipe(String recipeName);

    @Query("SELECT * FROM recipes r " +
            "INNER JOIN instruction_steps ins ON r.name = ins.recipe_name " +
            "INNER JOIN ingredients ing ON r.name = ing.recipe_name " +
            "WHERE r.name = :recipeName")
    LiveData<Recipe> getRecipeWithAllDetails(String recipeName);
}
