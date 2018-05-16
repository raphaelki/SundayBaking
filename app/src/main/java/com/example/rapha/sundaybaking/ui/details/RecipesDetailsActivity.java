package com.example.rapha.sundaybaking.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.ui.common.ActivityHelper;
import com.example.rapha.sundaybaking.ui.instructions.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.player.PlayerFragment;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class RecipesDetailsActivity extends AppCompatActivity
        implements InstructionStepClickCallback {

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
                        .add(R.id.video_fragment_frame, PlayerFragment.forRecipe(recipeName, 0), Constants.PLAYER_FRAGMENT_TAG)
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


    @Override
    public void onStepSelected(String recipeName, int stepNo) {
        Timber.d("Instruction step selected: %s", stepNo);
        if (deviceIsTablet) {
            showStepDetailsFor(stepNo);
            showVideoFor(stepNo);
        } else {
            showStepActivity(recipeName, stepNo);
        }
    }

    private void showStepActivity(String recipeName, int stepNo) {
        Intent intent = new Intent(this, PhoneStepsActivity.class);
        intent.putExtra(Constants.RECIPE_NAME_KEY, recipeName);
        intent.putExtra(Constants.RECIPE_STEP_NO_KEY, stepNo);
        startActivity(intent);
    }

    private void showStepDetailsFor(int stepNo) {
        InstructionsFragment instructionsFragment = (InstructionsFragment) getSupportFragmentManager().findFragmentByTag(Constants.INSTRUCTIONS_FRAGMENT_TAG);
        if (instructionsFragment != null) {
            instructionsFragment.setStepPage(stepNo);
        }
    }

    private void showVideoFor(int stepNo) {
        PlayerFragment playerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentByTag(Constants.PLAYER_FRAGMENT_TAG);
        if (playerFragment != null) {
            playerFragment.changeCurrentStep(stepNo);
        }
    }
}
