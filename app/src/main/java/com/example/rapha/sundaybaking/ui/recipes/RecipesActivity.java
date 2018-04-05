package com.example.rapha.sundaybaking.ui.recipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Recipe;

import timber.log.Timber;

public class RecipesActivity extends AppCompatActivity {

    private RecipesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        viewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null) {
                for (Recipe recipe : recipes) {
                    Timber.d("Recipe: " + recipe.getName());
                }
            }
        });
    }
}
