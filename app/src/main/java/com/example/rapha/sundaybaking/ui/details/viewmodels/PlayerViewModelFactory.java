package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;

public class PlayerViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String recipeName;
    private RecipeRepository recipeRepository;

    public PlayerViewModelFactory(@NonNull Application application, String recipeName) {
        this.application = application;
        this.recipeRepository = ((SundayBakingApp) application).getRecipeRepository();
        this.recipeName = recipeName;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PlayerViewModel(application, recipeRepository, recipeName);
    }
}
