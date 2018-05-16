package com.example.rapha.sundaybaking.ui;

import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.ui.recipes.RecipesActivity;
import com.example.rapha.sundaybaking.util.EspressoIdlingResource;
import com.example.rapha.sundaybaking.util.EspressoTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/*
 * Integration and navigation tests for RecipesActivity
 */
public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> activityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void initialization() {
        EspressoTestUtil.disableAnimations(activityTestRule);
        // Register idling resource to halt testing while fetching new data from the network
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void activityOpens_showsRecyclerViewInItsFragment() {
        onView(withId(R.id.recipes_rc)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnFirstItemOfRecyclerView_opensDetailsActivityWithIngredientsAndInstructionSteps() {
        onView(withContentDescription(R.string.recipe_rv_cd)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withContentDescription(R.string.ingredient_list_cd)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.instruction_steps_list_cd)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.recipe_rv_cd)).check(doesNotExist());
    }

    @Test
    public void swipeDownOnRecipesList_refreshesRecipes() {
        onView(withId(R.id.recipes_swipe_refresh)).perform(swipeDown());
        checkSnackBarMessage(R.string.recipes_refreshed_message);
    }

    private void deleteRecipeDatabase() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(RecipeDatabase.NAME);
    }

    @After
    public void dismissIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    private void checkSnackBarMessage(@StringRes int stringResId) {
        onView(withText(stringResId)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}