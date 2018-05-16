package com.example.rapha.sundaybaking.ui.recipes;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.DataState;
import com.example.rapha.sundaybaking.databinding.FragmentRecipesBinding;
import com.example.rapha.sundaybaking.ui.common.ViewModelFactory;
import com.example.rapha.sundaybaking.ui.details.RecipesDetailsActivity;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class RecipesFragment extends Fragment implements RecipeClickCallback {

    public static final String TAG = "ProductListViewModel";

    private FragmentRecipesBinding binding;
    @VisibleForTesting
    public RecipesViewModel viewModel;
    @VisibleForTesting
    public ViewModelProvider.Factory viewModelFactory;
    private RecipesAdapter recipesAdapter;

    public RecipesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false);
        recipesAdapter = new RecipesAdapter(this);
        binding.recipesRc.setAdapter(recipesAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        binding.recipesSwipeRefresh.setOnRefreshListener(() -> viewModel.triggerUpdate());
        viewModel.getRecipes().observe(this, recipes -> {
            Timber.d("Observed recipes changed");
            if (recipes != null) {
                recipesAdapter.setRecipeList(recipes);
                binding.setRecipes(recipes);
            }
        });
        viewModel.getDataState().observe(this, dataState -> {
            binding.recipesSwipeRefresh.setRefreshing(dataState == DataState.FETCHING);
            if (dataState != null) {
                switch (dataState) {
                    case ERROR:
                        showSnackbar(R.string.no_connection_message);
                        break;
                    case SUCCESS:
                        showSnackbar(R.string.recipes_refreshed_message);
                        break;
                }
            }
        });
    }

    private void showSnackbar(@StringRes int messageStringId) {
        Snackbar.make(getView(), messageStringId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRecipeSelected(String recipeName) {
        Intent intent = new Intent(getContext(), RecipesDetailsActivity.class);
        intent.putExtra(Constants.RECIPE_NAME_KEY, recipeName);
        startActivity(intent);
    }

    /*
     * Only create viewModelFactory if not running in test
     */
    private void createViewModel() {
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelFactory.getInstance(getActivity().getApplication());
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel.class);
    }
}
