package com.example.rapha.sundaybaking.ui.details.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.databinding.FragmentPlayerBinding;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModel;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

import java.util.List;

public class PlayerFragment extends Fragment {

    private PlayerViewModel viewModel;
    private FragmentPlayerBinding binding;
    private List<InstructionStep> steps;
    private int initialStepNo;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialStepNo = getArguments().getInt(Constants.RECIPE_STEP_NO_KEY);
        createViewModel();
        viewModel.getSteps().observe(this, steps -> {
            if (steps != null) {
                this.steps = steps;
                setSelectedStep(initialStepNo);
            }
        });
        binding.playerView.setPlayer(viewModel.getPlayerInstance());
    }

    public void setSelectedStep(int stepNo) {
        if (steps != null) {
            String videoUrl = steps.get(stepNo).getVideoURL();
            viewModel.startVideo(videoUrl);
        }
    }

    private void createViewModel() {
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        PlayerViewModelFactory viewModelFactory = new PlayerViewModelFactory(
                getActivity().getApplication(), recipeName);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel.class);
    }
}
