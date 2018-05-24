package com.example.rapha.sundaybaking.ui.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        if (savedInstanceState == null) {
            RecipesFragment recipesFragment = new RecipesFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_frame, recipesFragment, RecipesFragment.TAG)
                    .commit();
        }
    }
}
