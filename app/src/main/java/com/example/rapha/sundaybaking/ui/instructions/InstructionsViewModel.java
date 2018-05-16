package com.example.rapha.sundaybaking.ui.instructions;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

public class InstructionsViewModel extends ViewModel {

    private RecipeRepository repository;
    private final MutableLiveData<String> recipeName = new MutableLiveData<>();

    public InstructionsViewModel(Application application, RecipeRepository repository) {
        this.repository = repository;
    }

    public void changeCurrentRecipe(String name) {
        recipeName.setValue(name);
    }

    public LiveData<List<InstructionStep>> getInstructionSteps() {
        return Transformations.switchMap(recipeName,
                name -> repository.getInstructionSteps(name));
    }
}
