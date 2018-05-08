package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import timber.log.Timber;

public class PlayerViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    final private RecipeRepository repository;
    final private String recipeName;
    final private MutableLiveData<String> videoUrl = new MutableLiveData<>();
    final private MutableLiveData<InstructionStep> currentStep = new MutableLiveData<>();
    final private MutableLiveData<Integer> currentStepNo = new MutableLiveData<>();

    public PlayerViewModel(@NonNull Application application, RecipeRepository recipeRepository, String recipeName) {
        Timber.d("Creating PlayerViewModel for recipe: %s", recipeName);
        repository = recipeRepository;
        this.recipeName = recipeName;
    }

    public void changeCurrentStep(int stepNo) {
        currentStepNo.setValue(stepNo);
    }

    private String checkVideoAvailabilityAndSetVideoUrl(InstructionStep step) {
        String url = "";
        if (!step.getVideoURL().isEmpty()) url = step.getVideoURL();
        else if (!step.getThumbnailURL().isEmpty()) url = step.getThumbnailURL();
        return url;
    }

    public LiveData<String> getVideoUrl() {
        return Transformations.switchMap(currentStepNo, stepNo -> {
            LiveData<InstructionStep> step = repository.getInstructionStep(recipeName, stepNo);
            return Transformations.map(step, this::checkVideoAvailabilityAndSetVideoUrl);
        });
    }

    public LiveData<InstructionStep> getSelectedStep() {
        return Transformations.switchMap(currentStepNo,
                stepNo -> repository.getInstructionStep(recipeName, stepNo));
    }
}
