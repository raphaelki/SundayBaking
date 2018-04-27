package com.example.rapha.sundaybaking.ui.details.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.databinding.FragmentPlayerBinding;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModel;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class PlayerFragment extends Fragment {

    private PlayerViewModel viewModel;
    private FragmentPlayerBinding binding;
    private int selectedStep = -1;
    private int orientation;
    private Handler handler;
    private Runnable hideSystemUIRunnable;
    private boolean isTablet;

    public PlayerFragment() {
    }

    public static PlayerFragment forRecipe(String recipeName, int stepNo) {
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RECIPE_NAME_KEY, recipeName);
        bundle.putInt(Constants.RECIPE_STEP_NO_KEY, stepNo);
        playerFragment.setArguments(bundle);
        return playerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("PlayerFragment created");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        handler = new Handler();
        orientation = getResources().getConfiguration().orientation;
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (visibility -> {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        hideSystemBarsInLandscapeMode(2);
                        // TODO: show player controls for desired time
                    } else {
                        // TODO: hide player controls
                    }
                });

        return binding.getRoot();
    }

    private void hideSystemBarsInLandscapeMode(int delayInSec) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            hideSystemUIRunnable = this::hideSystemUI;
            handler.postDelayed(hideSystemUIRunnable, delayInSec * 1000);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        if (savedInstanceState == null)
            playVideoForStep(getArguments().getInt(Constants.RECIPE_STEP_NO_KEY));
        else playVideoForStep(savedInstanceState.getInt(Constants.RECIPE_STEP_NO_KEY));
        binding.playerView.setPlayer(viewModel.getPlayerInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
        configureFullScreenModeForLandscapeMode();
        hideSystemBarsInLandscapeMode(1);
    }

    private void createViewModel() {
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        PlayerViewModelFactory viewModelFactory = new PlayerViewModelFactory(
                getActivity().getApplication(), recipeName);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(hideSystemUIRunnable);
        viewModel.stopPlayer();
    }

    public void playVideoForStep(int stepNo) {
        if (selectedStep != stepNo) {
            Timber.d("Showing video for step: %s", stepNo);
            selectedStep = stepNo;
            viewModel.getSelectedStep(stepNo).removeObservers(this);
            viewModel.getSelectedStep(stepNo).observe(this, step -> {
                binding.setStep(step);
                viewModel.startVideo(step);
            });
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.RECIPE_STEP_NO_KEY, selectedStep);
    }

    private void configureFullScreenModeForLandscapeMode() {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    private void hideSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;
    }
}
