package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

public class RecipeDetailsViewModel extends ViewModel {

    private final RecipeRepository recipeRepository;
    private LiveData<List<InstructionStep>> instructionSteps;
    private LiveData<List<Ingredient>> ingredients;

    public RecipeDetailsViewModel(SundayBakingApp application, String recipeName) {
        this.recipeRepository = application.getRecipeRepository();
        instructionSteps = recipeRepository.getInstructionSteps(recipeName);
        ingredients = recipeRepository.getIngredients(recipeName);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public LiveData<List<InstructionStep>> getInstructionSteps() {
        return instructionSteps;
    }
}
