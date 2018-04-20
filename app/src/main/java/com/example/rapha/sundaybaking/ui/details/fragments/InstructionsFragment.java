package com.example.rapha.sundaybaking.ui.details.fragments;

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
import com.example.rapha.sundaybaking.ui.details.InstructionsFragmentCallback;
import com.example.rapha.sundaybaking.ui.details.adapters.InstructionsPagerAdapter;
import com.example.rapha.sundaybaking.ui.details.viewmodels.RecipeDetailsViewModel;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class InstructionsFragment extends Fragment {

    private InstructionsPagerAdapter pagerAdapter;
    private RecipeDetailsViewModel viewModel;
    private InstructionsFragmentCallback callback;
    private FragmentInstructionsBinding binding;

    public InstructionsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (InstructionsFragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InstructionsFragmentCallback");
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
        Timber.d("onCreateView");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false);
        pagerAdapter = new InstructionsPagerAdapter();
        binding.instructionsViewPager.setAdapter(pagerAdapter);
        binding.instructionsViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                callback.onStepChanged(position);
                Timber.d("Scrolled to page: %s", position);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.d("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        // use shared ViewModel created in RecipeDetailsFragment
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        viewModel.getInstructionSteps().observe(this, steps -> {
            pagerAdapter.setStepList(steps);
            setStepPage(getArguments().getInt(Constants.RECIPE_STEP_NO_KEY));
        });
    }

    public void setStepPage(int stepNo) {
        binding.instructionsViewPager.setCurrentItem(stepNo);
    }
}
