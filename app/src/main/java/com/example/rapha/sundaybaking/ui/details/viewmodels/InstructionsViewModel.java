package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

public class InstructionsViewModel extends ViewModel {

    private RecipeRepository repository;
    private LiveData<List<InstructionStep>> steps;

    public InstructionsViewModel(RecipeRepository repository, String recipeName) {
        this.repository = repository;
        steps = repository.getInstructionSteps(recipeName);
    }

    public LiveData<List<InstructionStep>> getInstructionSteps() {
        return steps;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private RecipeRepository repository;
        private String recipeName;

        public Factory(Application application, String recipeName) {
            repository = ((SundayBakingApp) application).getRecipeRepository();
            this.recipeName = recipeName;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new InstructionsViewModel(repository, recipeName);
        }
    }
}
