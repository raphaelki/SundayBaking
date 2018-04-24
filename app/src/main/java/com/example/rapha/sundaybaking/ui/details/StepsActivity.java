package com.example.rapha.sundaybaking.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.ui.details.fragments.InstructionsFragment;
import com.example.rapha.sundaybaking.ui.details.fragments.PlayerFragment;
import com.example.rapha.sundaybaking.util.Constants;

public class StepsActivity extends AppCompatActivity implements InstructionStepClickCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        String recipeName = getIntent().getStringExtra(Constants.RECIPE_NAME_KEY);
        int stepNo = getIntent().getIntExtra(Constants.RECIPE_STEP_NO_KEY, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.video_fragment_frame, PlayerFragment.forRecipe(recipeName, stepNo), Constants.PLAYER_FRAGMENT_TAG)
                    .add(R.id.instructions_fragment_frame, InstructionsFragment.forRecipe(recipeName, stepNo))
                    .commit();
        }
    }

    @Override
    public void onStepSelected(String recipeName, int stepNo) {
        showVideoFor(stepNo);
    }

    private void showVideoFor(int stepNo) {
        PlayerFragment playerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentByTag(Constants.PLAYER_FRAGMENT_TAG);
        if (playerFragment != null) {
            playerFragment.playVideoForStep(stepNo);
        }
    }
}
