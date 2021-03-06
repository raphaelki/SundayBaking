package com.example.rapha.sundaybaking.ui.common;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.ui.details.RecipeDetailsViewModel;
import com.example.rapha.sundaybaking.ui.instructions.SharedViewModel;
import com.example.rapha.sundaybaking.ui.recipes.RecipesViewModel;

/**
 * Common factory for providing the repository to all ViewModels
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private final RecipeRepository repository;
    private final Application application;

    private ViewModelFactory(@NonNull Application application) {
        this.application = application;
        repository = ((SundayBakingApp) application).getRecipeRepository();
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(repository);
        }
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel.class)) {
            return (T) new RecipeDetailsViewModel(repository);
        }
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(application, repository);
        }
        return super.create(modelClass);
    }

}
