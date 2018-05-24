package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
    private PlayerComponent playerComponent;
    private PlayerToolbarComponent playerToolbarComponent;
    private SharedViewModel viewModel;
    private PlayerListener playerListener;

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

        playerListener = playerState -> {
            if (viewModel != null) viewModel.setPlayerState(playerState);
        };

        playerComponent = new PlayerComponent(getContext(), binding.playerView, playerListener);
        getLifecycle().addObserver(playerComponent);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = getResources().getConfiguration().orientation;

        View decorView = getActivity().getWindow().getDecorView();
        playerToolbarComponent = new PlayerToolbarComponent(decorView, isTablet, orientation);
        getLifecycle().addObserver(playerToolbarComponent);

        return binding.getRoot();
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
        viewModel.getPlayerState().observe(this, playerComponent::setPlayerState);
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
    public void onDestroy() {
        super.onDestroy();
        playerListener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerToolbarComponent.setOrientation(newConfig.orientation);
    }
}
