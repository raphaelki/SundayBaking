package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.SundayBakingApp;

public class RecipeDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final String recipeName;
    private final Application application;

    public RecipeDetailsViewModelFactory(Application application, String recipeName) {
        this.recipeName = recipeName;
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeDetailsViewModel((SundayBakingApp) application, recipeName);
    }
}
