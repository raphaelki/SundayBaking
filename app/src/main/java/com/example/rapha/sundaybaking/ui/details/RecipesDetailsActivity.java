package com.example.rapha.sundaybaking.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.ui.common.ActivityHelper;
import com.example.rapha.sundaybaking.ui.instructions.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.instructions.PlayerFragment;
import com.example.rapha.sundaybaking.util.Constants;

public class RecipesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String recipeName = getIntent().getStringExtra(Constants.RECIPE_NAME_KEY);

        if (savedInstanceState == null) {
            replaceFragments(recipeName);
        }
    }

    private void replaceFragments(String recipeName) {
        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.forRecipe(recipeName);
        boolean deviceIsTablet = getResources().getBoolean(R.bool.isTablet);
        if (deviceIsTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_fragment_frame, detailsFragment)
                    .replace(R.id.instructions_fragment_frame, InstructionsFragment.forRecipe(recipeName, 0), Constants.INSTRUCTIONS_FRAGMENT_TAG)
                    .replace(R.id.video_fragment_frame, PlayerFragment.forRecipe(recipeName), Constants.PLAYER_FRAGMENT_TAG)
                    .commit();
        } else {
            ActivityHelper.setOrientationToPortraitMode(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_fragment_frame, detailsFragment)
                    .commit();
        }
    }

    /**
     * Called by widget when "Show recipe" button is clicked
     *
     * @param intent PendingIntent that is created in the widget
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String recipeName = intent.getStringExtra(Constants.RECIPE_NAME_KEY);
        if (recipeName != null) replaceFragments(recipeName);
    }
}
