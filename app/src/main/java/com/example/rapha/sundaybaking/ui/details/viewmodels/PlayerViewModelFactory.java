package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;

public class PlayerViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int stepId;
    private RecipeRepository recipeRepository;

    public PlayerViewModelFactory(@NonNull Application application, int stepId) {
        this.application = application;
        this.recipeRepository = ((SundayBakingApp) application).getRecipeRepository();
        this.stepId = stepId;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PlayerViewModel(application, recipeRepository, stepId);
    }
}
