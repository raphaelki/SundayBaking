package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.rapha.sundaybaking.ui.common.SharedViewModel;
import com.example.rapha.sundaybaking.ui.common.ViewModelFactory;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class InstructionsFragment extends Fragment {

    private InstructionsPagerAdapter pagerAdapter;
    ViewModelProvider.Factory viewModelFactory;
    private FragmentInstructionsBinding binding;
    private int stepPage = -1;
    private SharedViewModel viewModel;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("InstructionsFragment created");
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_instructions, container,
                false);
        pagerAdapter = new InstructionsPagerAdapter();
        binding.instructionsViewPager.setAdapter(pagerAdapter);
        binding.instructionsViewPager.addOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // update step no. in ViewModel to notify the PlayerFragment of the change
                        viewModel.changeCurrentStep(position);
                        stepPage = position;
                        Timber.d("Changed step no. on view pager to %s", position);
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
            // when data is loaded from database set the requested step page
            if (savedInstanceState == null) {
                setStepPage(getArguments().getInt(Constants.RECIPE_STEP_NO_KEY));
            } else {
                viewModel.getSelectedStep().observe(this, step -> {
                    setStepPage(step.getStepNo());
                });
            }
        });
    }

    /**
     * Accessed by the DetailsFragment on tablet
     *
     * @param stepNo Index of the desired page in ViewPager
     */
    public void setStepPage(int stepNo) {
        if (stepPage != stepNo) {
            binding.instructionsViewPager.setCurrentItem(stepNo);
            Timber.d("Showing instructions page for step: %s", stepNo);
            stepPage = stepNo;
        }
    }

    private void createViewModel() {
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelFactory.getInstance(getActivity().getApplication());
        }
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(SharedViewModel.class);
        String recipeName = getArguments().getString(Constants.RECIPE_NAME_KEY);
        viewModel.changeCurrentRecipe(recipeName);
    }
}
