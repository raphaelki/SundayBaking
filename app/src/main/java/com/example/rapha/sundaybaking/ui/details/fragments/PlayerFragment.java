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
import com.example.rapha.sundaybaking.databinding.FragmentPlayerBinding;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModel;
import com.example.rapha.sundaybaking.ui.details.viewmodels.PlayerViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

public class PlayerFragment extends Fragment {

    private PlayerViewModel viewModel;
    private FragmentPlayerBinding binding;

    public PlayerFragment() {
    }

    public static PlayerFragment forUrl(int stepId) {
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.STEP_ID_KEY, stepId);
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
        createViewModel();
        binding.playerView.setPlayer(viewModel.getPlayerInstance());
    }

    private void createViewModel() {
        PlayerViewModelFactory viewModelFactory = new PlayerViewModelFactory(
                getActivity().getApplication()
                , getArguments().getInt(Constants.STEP_ID_KEY));
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel.class);
        viewModel.getInstrucionStep().observe(this, instructionStep -> viewModel.startVideo(instructionStep.getVideoURL()));
    }
}
