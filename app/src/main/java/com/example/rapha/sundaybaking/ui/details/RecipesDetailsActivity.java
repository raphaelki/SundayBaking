package com.example.rapha.sundaybaking.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.ui.common.ActivityHelper;
import com.example.rapha.sundaybaking.ui.instructions.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.instructions.PlayerFragment;
import com.example.rapha.sundaybaking.util.Constants;

public class RecipesDetailsActivity extends AppCompatActivity {

    private boolean deviceIsTablet;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceIsTablet = getResources().getBoolean(R.bool.isTablet);
        recipeName = getIntent().getStringExtra(Constants.RECIPE_NAME_KEY);

        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.forRecipe(recipeName);

        if (savedInstanceState == null) {
            if (deviceIsTablet) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.details_fragment_frame, detailsFragment)
                        .add(R.id.instructions_fragment_frame, InstructionsFragment.forRecipe(recipeName, 0), Constants.INSTRUCTIONS_FRAGMENT_TAG)
                        .add(R.id.video_fragment_frame, PlayerFragment.forRecipe(recipeName), Constants.PLAYER_FRAGMENT_TAG)
                        .commit();
            } else {
                ActivityHelper.setOrientationToPortraitMode(this);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.details_fragment_frame, detailsFragment)
                        .commit();
            }
        }
    }
}
