package com.example.rapha.sundaybaking.ui.instructions;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

import timber.log.Timber;

/**
 * Shared ViewModel for PlayerFragment and InstructionsFragment
 */
public class SharedViewModel extends ViewModel {

    final private RecipeRepository repository;
    final private MutableLiveData<String> recipeName = new MutableLiveData<>();
    final private MutableLiveData<Integer> currentStepNo = new MutableLiveData<>();
    final private MutableLiveData<Boolean> deviceIsOnline = new MutableLiveData<>();
    private Application application;

    public SharedViewModel(Application application, RecipeRepository repository) {
        this.repository = repository;
        this.application = application;
    }

    public void changeCurrentStep(int stepNo) {
        Timber.d("Changing step to %s", stepNo);
        currentStepNo.setValue(stepNo);
    }

    public void changeCurrentRecipe(String name) {
        Timber.d("Changing recipe to %s", name);
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
                    checkDataConnection();
                    LiveData<InstructionStep> step = repository.getInstructionStep(name, stepNo);
                    return Transformations.map(step, this::checkVideoAvailabilityAndSetVideoUrl);
                }));
    }

    public LiveData<Boolean> getConnectionAvailability() {
        return deviceIsOnline;
    }

    private void checkDataConnection() {
        deviceIsOnline.setValue(((SundayBakingApp) application).deviceIsOnline());
    }

    public LiveData<InstructionStep> getSelectedStep() {
        return Transformations.switchMap(recipeName, name ->
                Transformations.switchMap(currentStepNo, step ->
                        repository.getInstructionStep(name, step)));
    }

    public LiveData<List<InstructionStep>> getInstructionSteps() {
        return Transformations.switchMap(recipeName,
                repository::getInstructionSteps);
    }
}
