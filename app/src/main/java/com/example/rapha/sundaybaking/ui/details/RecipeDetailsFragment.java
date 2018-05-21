package com.example.rapha.sundaybaking.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.databinding.FragmentRecipeDetailsBinding;
import com.example.rapha.sundaybaking.ui.common.ViewModelFactory;
import com.example.rapha.sundaybaking.ui.instructions.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.instructions.PhoneStepsActivity;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class RecipeDetailsFragment extends Fragment {

    private RecipeDetailsViewModel viewModel;
    private IngredientAdapter ingredientAdapter;
    private InstructionStepAdapter stepAdapter;
    ViewModelProvider.Factory viewModelFactory;
    private boolean deviceIsTablet;

    private final InstructionStepClickCallback callback = (recipeName, stepNo) -> {
        if (deviceIsTablet) {
            showStepDetailsFor(stepNo);
        } else {
            showStepActivity(recipeName, stepNo);
        }
    };

    private void showStepActivity(String recipeName, int stepNo) {
        Intent intent = new Intent(getContext(), PhoneStepsActivity.class);
        intent.putExtra(Constants.RECIPE_NAME_KEY, recipeName);
        intent.putExtra(Constants.RECIPE_STEP_NO_KEY, stepNo);
        startActivity(intent);
    }

    public static RecipeDetailsFragment forRecipe(String recipeName) {
        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RECIPE_NAME_KEY, recipeName);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        deviceIsTablet = getResources().getBoolean(R.bool.isTablet);
        Timber.d("RecipeDetailsFragment created");
        FragmentRecipeDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);
        ingredientAdapter = new IngredientAdapter();
        binding.ingredientsRv.setAdapter(ingredientAdapter);
        binding.ingredientsRv.setNestedScrollingEnabled(false);
        stepAdapter = new InstructionStepAdapter(callback);
        binding.stepsRv.setAdapter(stepAdapter);
        binding.stepsRv.setNestedScrollingEnabled(false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        getActivity().setTitle(recipeName);
        viewModel.changeCurrentRecipe(recipeName);
        viewModel.getIngredients().observe(this, ingredients -> ingredientAdapter.setIngredientList(ingredients));
        viewModel.getInstructionSteps().observe(this, instructionSteps -> stepAdapter.setStepList(instructionSteps));
    }

    private void createViewModel() {
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelFactory.getInstance(getActivity().getApplication());
        }
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(RecipeDetailsViewModel.class);
    }

    private void showStepDetailsFor(int stepNo) {
        InstructionsFragment instructionsFragment = (InstructionsFragment) getFragmentManager().findFragmentByTag(Constants.INSTRUCTIONS_FRAGMENT_TAG);
        if (instructionsFragment != null) {
            instructionsFragment.setStepPage(stepNo);
        }
    }
}
