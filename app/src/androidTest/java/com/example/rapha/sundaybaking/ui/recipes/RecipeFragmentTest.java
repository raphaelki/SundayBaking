package com.example.rapha.sundaybaking.ui.recipes;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rapha.sundaybaking.EspressoTestUtil;
import com.example.rapha.sundaybaking.FragmentTestingActivity;
import com.example.rapha.sundaybaking.TestUtil;
import com.example.rapha.sundaybaking.ViewModelFactory;
import com.example.rapha.sundaybaking.ViewModelUtil;
import com.example.rapha.sundaybaking.data.DataState;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {

    @Rule
    public ActivityTestRule<FragmentTestingActivity> activityTestRule = new ActivityTestRule<>(FragmentTestingActivity.class);

    private MutableLiveData<DataState> dataState = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private RecipesViewModel viewModel;

    @Before
    public void startup() {
        EspressoTestUtil.disableAnimations(activityTestRule);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());

        RecipesFragment fragment = new RecipesFragment();
        viewModel = mock(RecipesViewModel.class);
        when(viewModel.getRecipes()).thenReturn(recipes);
        when(viewModel.getDataState()).thenReturn(dataState);
        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        activityTestRule.getActivity().setFragment(fragment);
        ViewModelFactory.destroyInstance();
    }

    @Test
    public void firstTest() {
        recipes.postValue(TestUtil.createRecipes());
        onView(withText("Kiwi cake")).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
