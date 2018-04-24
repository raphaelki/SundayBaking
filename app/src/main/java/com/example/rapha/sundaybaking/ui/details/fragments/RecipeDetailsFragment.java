package com.example.rapha.sundaybaking.ui.details.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.example.rapha.sundaybaking.ui.details.InstructionStepClickCallback;
import com.example.rapha.sundaybaking.ui.details.adapters.IngredientAdapter;
import com.example.rapha.sundaybaking.ui.details.adapters.InstructionStepAdapter;
import com.example.rapha.sundaybaking.ui.details.viewmodels.RecipeDetailsViewModel;
import com.example.rapha.sundaybaking.ui.details.viewmodels.RecipeDetailsViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class RecipeDetailsFragment extends Fragment {

    private RecipeDetailsViewModel viewModel;
    private FragmentRecipeDetailsBinding binding;
    private IngredientAdapter ingredientAdapter;
    private InstructionStepAdapter stepAdapter;

    private InstructionStepClickCallback callback;

    public RecipeDetailsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (InstructionStepClickCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InstructionStepClickCallback");
        }
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
        Timber.d("RecipeDetailsFragment created");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);
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
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        RecipeDetailsViewModelFactory factory = new RecipeDetailsViewModelFactory(getActivity().getApplication(), recipeName);
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(RecipeDetailsViewModel.class);
        viewModel.getIngredients().observe(this, ingredients -> ingredientAdapter.setIngredientList(ingredients));
        viewModel.getInstructionSteps().observe(this, instructionSteps -> stepAdapter.setStepList(instructionSteps));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("RecipeDetailsFragment destroyed");
    }
}
