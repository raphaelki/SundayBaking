package com.example.rapha.sundaybaking.data;

import android.arch.lifecycle.LiveData;

import com.example.rapha.sundaybaking.AppExecutors;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.data.local.RecipesDao;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.data.remote.RecipesRemoteAPI;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Response;
import timber.log.Timber;

public class RecipeRepository implements RecipeDataSource {

    private RecipesRemoteAPI recipesRemoteAPI;
    private AppExecutors appExecutors;
    private RecipeDatabase recipeDatabase;

    private static RecipeRepository INSTANCE;

    private RecipeRepository(RecipesRemoteAPI recipesRemoteAPI, RecipeDatabase recipeDatabase, AppExecutors appExecutors) {
        this.recipesRemoteAPI = recipesRemoteAPI;
        this.recipeDatabase = recipeDatabase;
        this.appExecutors = appExecutors;
    }

    public static RecipeRepository getInstance(final AppExecutors appExecutors, final RecipesRemoteAPI recipesRemoteAPI, final RecipeDatabase recipeDatabase) {
        if (INSTANCE == null){
            synchronized (RecipeRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new RecipeRepository(recipesRemoteAPI, recipeDatabase, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Recipe>> getRecipes(){
        fetchRecipes();
        return recipeDatabase.recipesDao().getRecipes();
    }

    private void fetchRecipes(){
        appExecutors.diskIO().execute(() -> {
            Response<List<Recipe>> response;
            try {
               response = recipesRemoteAPI.getRecipes().execute();
               List<Recipe> recipes = response.body();
               if (recipes != null){
                   recipeDatabase.recipesDao().insertRecipes(recipes);
                   for (Recipe recipe : recipes) {
                       recipeDatabase.ingredientsDao().insertIngredients(recipe.getIngredients());
                       recipeDatabase.instructionStepsDao().insertInstructionSteps(recipe.getSteps());
                   }
               }
            } catch (IOException e) {
                Timber.e(e, "error fetching recipe data from remote source");
            }
        });
    }
}
