package com.example.rapha.sundaybaking.ui.common;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.ui.details.RecipeDetailsViewModel;
import com.example.rapha.sundaybaking.ui.instructions.InstructionsViewModel;
import com.example.rapha.sundaybaking.ui.player.PlayerViewModel;
import com.example.rapha.sundaybaking.ui.recipes.RecipesViewModel;

/**
 *
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private RecipeRepository repository;
    private Application application;

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

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(application, repository);
        }
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel.class)) {
            return (T) new RecipeDetailsViewModel(application, repository);
        }
        if (modelClass.isAssignableFrom(PlayerViewModel.class)) {
            return (T) new PlayerViewModel(application, repository);
        }
        if (modelClass.isAssignableFrom(InstructionsViewModel.class)) {
            return (T) new InstructionsViewModel(application, repository);
        }
        return super.create(modelClass);
    }

}
