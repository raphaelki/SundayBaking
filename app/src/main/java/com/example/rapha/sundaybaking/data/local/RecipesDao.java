package com.example.rapha.sundaybaking.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    LiveData<Recipe> getRecipe(int recipeId);

    @Query("SELECT * FROM recipes r " +
            "INNER JOIN instruction_steps ins ON r.id = ins.recipe_id " +
            "INNER JOIN ingredients ing ON r.id = ing.recipe_id " +
            "WHERE r.id = :recipeId")
    LiveData<Recipe> getRecipeWithAllDetails(int recipeId);
}
