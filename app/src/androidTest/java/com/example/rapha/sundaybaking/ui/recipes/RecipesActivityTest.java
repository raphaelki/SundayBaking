package com.example.rapha.sundaybaking.ui.recipes;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.rapha.sundaybaking.EspressoTestUtil;
import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.util.EspressoIdlingResource;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> activityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void disableRecyclerViewAnimations() {
        EspressoTestUtil.disableAnimations(activityTestRule);
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
        onView(withContentDescription(R.string.recipe_rv_cd)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    private void deleteRecipeDatabase() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(RecipeDatabase.NAME);
    }

    @After
    public void dismissIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}