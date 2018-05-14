package com.example.rapha.sundaybaking.ui.recipes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.DataState;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private final RecipeRepository recipeRepository;

    public RecipesViewModel(Application application){
        super(application);
        recipeRepository = ((SundayBakingApp) application).getRecipeRepository();
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
