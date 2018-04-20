package com.example.rapha.sundaybaking.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.ui.details.fragments.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.details.fragments.PlayerFragment;
import com.example.rapha.sundaybaking.ui.details.fragments.RecipeDetailsFragment;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class RecipesDetailsActivity extends AppCompatActivity
        implements InstructionStepClickCallback, InstructionsFragmentCallback {

    private RecipeDetailsFragment detailsFragment;
    private FrameLayout upperFragmentFrame;
    private boolean deviceIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);

        deviceIsTablet = getResources().getBoolean(R.bool.isTablet);

        upperFragmentFrame = findViewById(R.id.upper_fragment_frame);

        if (savedInstanceState == null) {
            String recipeName = getIntent().getStringExtra(Constants.RECIPE_NAME_KEY);
            detailsFragment = new RecipeDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.RECIPE_NAME_KEY, recipeName);
            detailsFragment.setArguments(bundle);

            if (!deviceIsTablet) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.lower_fragment_frame, detailsFragment)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.left_fragment_frame, detailsFragment)
                        .add(R.id.lower_fragment_frame, InstructionsFragment.forRecipe(recipeName, 0), Constants.INSTRUCTIONS_FRAGMENT_TAG)
                        .add(R.id.upper_fragment_frame, PlayerFragment.forRecipe(recipeName, 0), Constants.PLAYER_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onClick(String recipeName, int stepNo) {
        Timber.d("%s. instruction Step clicked", stepNo);
        if (!deviceIsTablet) {
            createInstructionsAndVideoFragments(recipeName, stepNo);
        } else {
            showStepDetails(stepNo);
        }

    }

    private void createInstructionsAndVideoFragments(String recipeName, int stepNo) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.lower_fragment_frame, InstructionsFragment.forRecipe(recipeName, stepNo), Constants.INSTRUCTIONS_FRAGMENT_TAG)
                .replace(R.id.upper_fragment_frame, PlayerFragment.forRecipe(recipeName, stepNo), Constants.PLAYER_FRAGMENT_TAG)
                .commit();
        upperFragmentFrame.setVisibility(View.VISIBLE);
    }

    private void showStepDetails(int stepNo) {
        changeStepNoForInstructions(stepNo);
        changeStepNoForPlayer(stepNo);
    }

    private void changeStepNoForInstructions(int stepNo) {
        InstructionsFragment instructionsFragment = (InstructionsFragment) getSupportFragmentManager().findFragmentByTag(Constants.INSTRUCTIONS_FRAGMENT_TAG);
        if (instructionsFragment != null) {
            instructionsFragment.setStepPage(stepNo);
        }
    }

    private void changeStepNoForPlayer(int stepNo) {
        PlayerFragment playerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentByTag(Constants.PLAYER_FRAGMENT_TAG);
        if (playerFragment != null) {
            playerFragment.setSelectedStep(stepNo);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!deviceIsTablet) {
            upperFragmentFrame.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStepChanged(int stepNo) {
        changeStepNoForPlayer(stepNo);
    }
}
