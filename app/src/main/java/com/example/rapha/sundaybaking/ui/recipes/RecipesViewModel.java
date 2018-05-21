package com.example.rapha.sundaybaking.ui.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.rapha.sundaybaking.data.DataState;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

public class RecipesViewModel extends ViewModel {

    private final RecipeRepository recipeRepository;

    public RecipesViewModel(RecipeRepository repository) {
        recipeRepository = repository;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

    public void triggerUpdate() {
        recipeRepository.triggerFetch();
    }

    public LiveData<DataState> getDataState() {
        return recipeRepository.getDataState();
    }
}
