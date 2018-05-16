package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.databinding.FragmentInstructionsBinding;
import com.example.rapha.sundaybaking.ui.common.ViewModelFactory;
import com.example.rapha.sundaybaking.ui.details.InstructionStepClickCallback;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class InstructionsFragment extends Fragment {

    private InstructionsPagerAdapter pagerAdapter;
    private InstructionsViewModel viewModel;
    private InstructionStepClickCallback callback;
    private FragmentInstructionsBinding binding;
    private int stepPage = -1;
    private String recipeName;

    public InstructionsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (InstructionStepClickCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InstructionStepClickCallback");
        }
    }

    public static InstructionsFragment forRecipe(String recipeName, int stepNo) {
        InstructionsFragment instructionsFragment = new InstructionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RECIPE_NAME_KEY, recipeName);
        bundle.putInt(Constants.RECIPE_STEP_NO_KEY, stepNo);
        instructionsFragment.setArguments(bundle);
        return instructionsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("InstructionsFragment created");
        recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false);
        pagerAdapter = new InstructionsPagerAdapter();
        binding.instructionsViewPager.setAdapter(pagerAdapter);
        binding.instructionsViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                callback.onStepSelected(recipeName, position);
                stepPage = position;
                Timber.d("Scrolled to page: %s", position);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        viewModel.getInstructionSteps().observe(this, steps -> {
            pagerAdapter.setStepList(steps);
            if (savedInstanceState != null) {
                setStepPage(savedInstanceState.getInt(Constants.RECIPE_STEP_NO_KEY));
            } else {
                setStepPage(getArguments().getInt(Constants.RECIPE_STEP_NO_KEY));
            }
        });
    }

    public void setStepPage(int stepNo) {
        if (stepPage != stepNo) {
            Timber.d("Showing instructions page for step: %s", stepNo);
            stepPage = stepNo;
            binding.instructionsViewPager.setCurrentItem(stepNo);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.RECIPE_STEP_NO_KEY, stepPage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("InstructionsFragment destroyed");
    }

    private void createViewModel() {
        viewModel = ViewModelProviders.of(this,
                ViewModelFactory.getInstance(getActivity().getApplication()))
                .get(InstructionsViewModel.class);
        viewModel.changeCurrentRecipe(recipeName);
    }
}
