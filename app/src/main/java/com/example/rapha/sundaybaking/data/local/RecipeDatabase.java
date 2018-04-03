package com.example.rapha.sundaybaking.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.data.models.Recipe;

@Database(entities = {Recipe.class, Ingredient.class, InstructionStep.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipesDao recipesDao();
}
