package com.example.rapha.sundaybaking.ui.recipes;

import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.StringRes;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.rapha.sundaybaking.EspressoTestUtil;
import com.example.rapha.sundaybaking.FragmentTestingActivity;
import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.TestUtil;
import com.example.rapha.sundaybaking.ViewModelFactory;
import com.example.rapha.sundaybaking.ViewModelUtil;
import com.example.rapha.sundaybaking.data.DataState;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.ui.details.RecipesDetailsActivity;
import com.example.rapha.sundaybaking.util.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.rapha.sundaybaking.SwipeRefreshLayoutMatchers.isRefreshing;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {

//    @Rule
//    public ActivityTestRule<FragmentTestingActivity> activityTestRule = new ActivityTestRule<>(FragmentTestingActivity.class);

    @Rule
    public IntentsTestRule<FragmentTestingActivity> intentsTestRule = new IntentsTestRule<>(FragmentTestingActivity.class);

    private MutableLiveData<DataState> dataState = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    @Mock
    private RecipesViewModel viewModel;

    @Before
    public void initialization() {
        MockitoAnnotations.initMocks(this);
        EspressoTestUtil.disableAnimations(intentsTestRule);

        RecipesFragment fragment = new RecipesFragment();
        when(viewModel.getRecipes()).thenReturn(recipes);
        when(viewModel.getDataState()).thenReturn(dataState);

        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        intentsTestRule.getActivity().setFragment(fragment);
        ViewModelFactory.destroyInstance();
    }

    @Test
    public void insertRecipesInViewModel_recipesAreDisplayedInRecyclerView() {
        String[] cakes = {"Kiwi cake", "Banana cake", "Sweet sweet sugar cake", "Brownies", "Chocolate cake", "Nougat pie", "Apple Pie"};
        List<Recipe> recipesList = TestUtil.createRecipes(cakes);
        recipes.postValue(recipesList);
        int position = 0;
        for (Recipe recipe : recipesList) {
            onView(withContentDescription(R.string.recipe_rv_cd)).perform(RecyclerViewActions.scrollToPosition(position));
            onView(withText(recipe.getName())).check(matches(isDisplayed()));
            position++;
        }
    }

    @Test
    public void setDataStateToFetching_showsLoadingIndicator() {
        dataState.postValue(DataState.FETCHING);
        onView(withId(R.id.recipes_swipe_refresh)).check(matches(isRefreshing()));
    }

    @Test
    public void setDataStateToSuccess_showsRefreshedMessage() {
        dataState.postValue(DataState.SUCCESS);
        checkSnackBarMessage(R.string.recipes_refreshed_message);
    }

    @Test
    public void setDataStateToError_showsErrorMessage() {
        dataState.postValue(DataState.ERROR);
        checkSnackBarMessage(R.string.no_connection_message);
    }

    @Test
    public void noRecipesAvailable_showTextViewMessage() {
        // set empty list
        recipes.postValue(TestUtil.createRecipes());
        onView(withId(R.id.no_recipes_available_tv)).check(matches(isDisplayed()));
    }

    @Test
    public void recipesAvailable_hideNoRecipesAvailableMessage() {
        recipes.postValue(TestUtil.createRecipes("Apple pie"));
        onView(withId(R.id.no_recipes_available_tv)).check(matches(not(isDisplayed())));
    }

    @Test
    public void swipeDownOnLayout_triggersUpdate() {
        onView(withId(R.id.recipes_swipe_refresh)).perform(ViewActions.swipeDown());
        verify(viewModel).triggerUpdate();
    }

    @Test
    public void clickOnRecipe_startDetailsActivityWithRecipeNameAsExtra() {
        intending(isInternal()).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
        recipes.postValue(TestUtil.createRecipes("Apple pie"));
        onView(withId(R.id.recipes_rc)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(allOf(hasComponent(RecipesDetailsActivity.class.getName()), hasExtraWithKey(Constants.RECIPE_NAME_KEY)));
    }

    private void checkSnackBarMessage(@StringRes int stringResId) {
        onView(withText(stringResId)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
