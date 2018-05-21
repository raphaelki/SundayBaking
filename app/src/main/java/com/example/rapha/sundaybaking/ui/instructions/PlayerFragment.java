package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.ViewModelProvider;
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
import com.example.rapha.sundaybaking.ui.common.ViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class PlayerFragment extends Fragment {

    ViewModelProvider.Factory viewModelFactory;
    private FragmentPlayerBinding binding;
    private int orientation;
    private Handler handler;
    private boolean isTablet;
    private PlayerComponent playerComponent;
    private SharedViewModel viewModel;

    public PlayerFragment() {
    }

    public static PlayerFragment forRecipe(String recipeName) {
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RECIPE_NAME_KEY, recipeName);
        playerFragment.setArguments(bundle);
        return playerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("PlayerFragment created");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false);

        playerComponent = new PlayerComponent(getContext(), binding.playerView);
        getLifecycle().addObserver(playerComponent);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        handler = new Handler();
        orientation = getResources().getConfiguration().orientation;
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (visibility -> {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        hideSystemBarsInLandscapeMode(2);
                    }
                });
        return binding.getRoot();
    }

    private void hideSystemBarsInLandscapeMode(int delayInSec) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            Runnable hideSystemUIRunnable = this::hideSystemUI;
            handler.postDelayed(hideSystemUIRunnable, delayInSec * 1000);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        viewModel.getVideoUrl().observe(this, videoUrl -> {
            if (videoUrl != null && !videoUrl.isEmpty()) {
                playerComponent.playVideo(videoUrl);
            }
        });
        viewModel.getSelectedStep().observe(this, binding::setStep);
        viewModel.getConnectionAvailability().observe(this, binding::setIsDataConnectionAvailable);
    }

    @Override
    public void onResume() {
        super.onResume();
        configureFullScreenModeForLandscapeMode();
        hideSystemBarsInLandscapeMode(1);
    }

    private void createViewModel() {
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelFactory.getInstance(
                    getActivity().getApplication());
        }
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(SharedViewModel.class);
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        viewModel.changeCurrentRecipe(recipeName);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
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
