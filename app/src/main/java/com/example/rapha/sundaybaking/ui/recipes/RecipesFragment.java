package com.example.rapha.sundaybaking.ui.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.databinding.FragmentRecipesBinding;

import timber.log.Timber;

public class RecipesFragment extends Fragment {

    public static final String TAG = "ProductListViewModel";

    private FragmentRecipesBinding binding;
    private RecipesViewModel viewModel;
    private RecipesAdapter recipesAdapter;

    public RecipesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false);
        recipesAdapter = new RecipesAdapter();
        binding.recipesRc.setAdapter(recipesAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        viewModel.getRecipes().observe(this, recipes -> {
            Timber.d("Observed recipes changed");
            if (recipes != null) {
                recipesAdapter.setRecipeList(recipes);
            }
        });
    }
}
