package com.example.rapha.sundaybaking.ui.player;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

public class PlayerViewModel extends ViewModel {

    final private RecipeRepository repository;
    final private MutableLiveData<String> recipeName = new MutableLiveData<>();
    final private MutableLiveData<Integer> currentStepNo = new MutableLiveData<>();

    public PlayerViewModel(@NonNull Application application, RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public void changeCurrentStep(int stepNo) {
        currentStepNo.setValue(stepNo);
    }

    public void changeCurrentRecipe(String name) {
        recipeName.setValue(name);
    }

    private String checkVideoAvailabilityAndSetVideoUrl(InstructionStep step) {
        String url = "";
        if (!step.getVideoURL().isEmpty()) url = step.getVideoURL();
        else if (!step.getThumbnailURL().isEmpty()) url = step.getThumbnailURL();
        return url;
    }

    public LiveData<String> getVideoUrl() {
        return Transformations.switchMap(recipeName, name ->
                Transformations.switchMap(currentStepNo, stepNo -> {
                    LiveData<InstructionStep> step = repository.getInstructionStep(name, stepNo);
                    return Transformations.map(step, this::checkVideoAvailabilityAndSetVideoUrl);
                }));
    }

    public LiveData<InstructionStep> getSelectedStep() {
        return Transformations.switchMap(recipeName, name ->
                Transformations.switchMap(currentStepNo, step ->
                        repository.getInstructionStep(name, step)));
    }
}
