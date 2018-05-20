package com.example.rapha.sundaybaking.ui.instructions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.util.Constants;

/*
 * Activity for displaying video fragment and recipe guidance on phone
 */
public class PhoneStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        String recipeName = getIntent().getStringExtra(Constants.RECIPE_NAME_KEY);
        int stepNo = getIntent().getIntExtra(Constants.RECIPE_STEP_NO_KEY, 0);

        setTitle(recipeName);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.video_fragment_frame, PlayerFragment.forRecipe(recipeName),
                            Constants.PLAYER_FRAGMENT_TAG)
                    .add(R.id.instructions_fragment_frame,
                            InstructionsFragment.forRecipe(recipeName, stepNo))
                    .commit();
        }
    }
}
