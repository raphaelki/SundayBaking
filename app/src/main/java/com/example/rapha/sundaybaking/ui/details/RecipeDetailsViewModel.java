package com.example.rapha.sundaybaking.ui.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

public class RecipeDetailsViewModel extends ViewModel {

    private final RecipeRepository recipeRepository;
    private final MutableLiveData<String> recipeName = new MutableLiveData<>();

    public RecipeDetailsViewModel(RecipeRepository repository) {
        this.recipeRepository = repository;
    }

    public void changeCurrentRecipe(@NonNull String recipeName) {
        this.recipeName.setValue(recipeName);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return Transformations.switchMap(recipeName, recipeRepository::getIngredients);
    }

    public LiveData<List<InstructionStep>> getInstructionSteps() {
        return Transformations.switchMap(recipeName, recipeRepository::getInstructionSteps);
    }
}
