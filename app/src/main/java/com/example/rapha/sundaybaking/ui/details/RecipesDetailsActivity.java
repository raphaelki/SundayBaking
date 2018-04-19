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

public class RecipesDetailsActivity extends AppCompatActivity implements InstructionStepClickCallback, InstructionsFragmentCallback {

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
                        .commit();
            }
        }
    }

    @Override
    public void onClick(int stepId) {
        Timber.d("Instruction Step clicked: %s", stepId);
        showStepDetails(stepId);
    }

    private void showStepDetails(int stepId) {
        if (!deviceIsTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.lower_fragment_frame, new InstructionsFragment())
                    .replace(R.id.upper_fragment_frame, PlayerFragment.forUrl(stepId))
                    .commit();
            upperFragmentFrame.setVisibility(View.VISIBLE);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.lower_fragment_frame, new InstructionsFragment())
                    .replace(R.id.upper_fragment_frame, PlayerFragment.forUrl(stepId))
                    .commit();
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
    public void onStepChanged(int stepId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.upper_fragment_frame, PlayerFragment.forUrl(stepId))
                .commit();
    }
}
