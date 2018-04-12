package com.example.rapha.sundaybaking.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.data.models.Recipe;

@Database(entities = {Recipe.class, Ingredient.class, InstructionStep.class}, version = 2)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipesDao recipesDao();
    public abstract InstructionStepsDao instructionStepsDao();
    public abstract IngredientsDao ingredientsDao();

    private static RecipeDatabase INSTANCE;
    private static final String NAME = "recipes.db";

    public static RecipeDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (RecipeDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
